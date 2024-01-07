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

/**
 * Repository interface for weather data.
 */
interface WeatherRepository {
    /**
     * Get weather data for a specific location as a flow.
     *
     * @param location The name or label for the location.
     * @return Flow emitting [Weather] data for the specified location.
     */
    fun getWeather(location: String): Flow<Weather?>

    /**
     * Insert weather data into the repository.
     *
     * @param weather The weather data to be inserted.
     */
    suspend fun insertWeather(weather: Weather)

    /**
     * Update existing weather data in the repository.
     *
     * @param weather The updated weather data.
     */
    suspend fun updateWeather(weather: Weather)

    /**
     * Refresh weather data for a specific location, updating the repository.
     *
     * @param location The name or label for the location.
     */
    suspend fun refresh(location: String)

    /**
     * Flow providing [WorkInfo] for the Wi-Fi notification worker.
     */
    var wifiWorkInfo: Flow<WorkInfo>
}

/**
 * Implementation of [WeatherRepository] with caching capabilities.
 *
 * @param weatherDao Data Access Object (DAO) for weather data.
 * @param weatherApiService API service for retrieving weather data.
 * @param context Application context for accessing system services.
 */
class CachingWeatherRepository(
    private val weatherDao: WeatherDao,
    private val weatherApiService: WeatherApiService,
    context: Context
) : WeatherRepository {

    private var workId = UUID(1, 2)
    private val workManager = WorkManager.getInstance(context)

    /**
     * Flow providing [WorkInfo] for the Wi-Fi notification worker.
     */
    override var wifiWorkInfo: Flow<WorkInfo> =
        workManager.getWorkInfoByIdFlow(workId)

    /**
     * Get weather data for a specific location as a flow.
     *
     * @param location The name or label for the location.
     * @return Flow emitting [Weather] data for the specified location.
     */
    override fun getWeather(location: String): Flow<Weather?> {
        return weatherDao.getWeather(location).map {
            it.asDomainWeatherObject()
        }.onEach {
            refresh(location)
        }
    }

    /**
     * Insert weather data into the repository.
     *
     * @param weather The weather data to be inserted.
     */
    override suspend fun insertWeather(weather: Weather) {
        weatherDao.insert(weather.asDbWeatherObject())
    }

    /**
     * Update existing weather data in the repository.
     *
     * @param weather The updated weather data.
     */
    override suspend fun updateWeather(weather: Weather) {
        weatherDao.update(weather.asDbWeatherObject())
    }

    /**
     * Refresh weather data for a specific location, updating the repository.
     *
     * @param location The name or label for the location.
     */
    override suspend fun refresh(location: String) {
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
