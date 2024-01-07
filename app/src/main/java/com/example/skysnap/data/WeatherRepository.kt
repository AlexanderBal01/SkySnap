package com.example.skysnap.data

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.skysnap.data.database.weather.WeatherDao
import com.example.skysnap.data.database.weather.asDbWeatherObject
import com.example.skysnap.data.database.weather.asDomainWeatherObject
import com.example.skysnap.model.Weather
import com.example.skysnap.network.WeatherApiService
import com.example.skysnap.network.asDomainObject
import com.example.skysnap.network.getWeatherAsFlow
import com.example.skysnap.workerUtils.WifiNotificationWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.net.SocketTimeoutException
import java.util.UUID

interface WeatherRepository {
    fun getWeather(location: String) : Flow<Weather?>

    suspend fun insertWeather(weather: Weather)

    suspend fun updateWeather(weather: Weather)

    suspend fun refresh(location: String)

    var wifiWorkInfo: Flow<WorkInfo>
}

class CachingWeatherRepository(private val weatherDao: WeatherDao, private val weatherApiService: WeatherApiService, context: Context) : WeatherRepository {
    override fun getWeather(location: String): Flow<Weather?> {
        return weatherDao.getWeather(location).map {
            it.asDomainWeatherObject()
        }.onEach {
            refresh(location)
        }
    }

    override suspend fun insertWeather(weather: Weather) {
        weatherDao.insert(weather.asDbWeatherObject())
    }

    override suspend fun updateWeather(weather: Weather) {
        weatherDao.update(weather.asDbWeatherObject())
    }

    private var workId = UUID(1,2)
    private val workManager = WorkManager.getInstance(context)
    override var wifiWorkInfo: Flow<WorkInfo> =
        workManager.getWorkInfoByIdFlow(workId)
    override suspend fun refresh(location: String) {
        Log.i("test", location)
        val constraints = Constraints
            .Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val requestBuilder = OneTimeWorkRequestBuilder<WifiNotificationWorker>()
        val request = requestBuilder.setConstraints(constraints).build()
        workManager.enqueue(request)
        workId = request.id
        wifiWorkInfo = workManager.getWorkInfoByIdFlow(request.id)

        try {
            weatherApiService
                .getWeatherAsFlow(location = location)
                .asDomainObject()
                .collect {
                    Log.i("CHECK", "refresh: $it")
                    insertWeather(it)
                }
        } catch (e: SocketTimeoutException) {
            Log.e("REFRESH", "REFRESH: ${e.stackTraceToString()}")
        }
    }
}