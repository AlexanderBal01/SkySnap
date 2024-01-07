package com.example.skysnap.data

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.skysnap.data.database.location.LocationDao
import com.example.skysnap.data.database.location.asDbLocationObject
import com.example.skysnap.data.database.location.asDomainLocationObject
import com.example.skysnap.data.database.location.asDomainLocationObjects
import com.example.skysnap.model.Location
import com.example.skysnap.network.LocationApiService
import com.example.skysnap.network.asDomainObject
import com.example.skysnap.network.getLocationAsFlow
import com.example.skysnap.workerUtils.WifiNotificationWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.net.SocketTimeoutException
import java.util.UUID

interface LocationRepository {
    fun getLocation(location: String): Flow<Location?>

    fun getAll(): Flow<List<Location>>

    suspend fun insertLocation(location: Location)

    suspend fun refresh(location: String)

    var wifiWorkInfo: Flow<WorkInfo>
}

class CachingLocationRepository(private val locationDao: LocationDao, private val locationApiService: LocationApiService, context: Context) : LocationRepository {
    override fun getLocation(location: String): Flow<Location?> {
        return locationDao.getItem(location).map {
            it.asDomainLocationObject()
        }.onEach {
            refresh(location)
        }
    }

    override fun getAll(): Flow<List<Location>> {
        return locationDao.getAllItems().map {
            it.asDomainLocationObjects()
        }
    }

    override suspend fun insertLocation(location: Location) {
        locationDao.insert(location.asDbLocationObject())
    }

    private var workId = UUID(1,2)
    private val workManager = WorkManager.getInstance(context)
    override var wifiWorkInfo: Flow<WorkInfo> =
        workManager.getWorkInfoByIdFlow(workId)
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
            locationApiService
                .getLocationAsFlow(location = location)
                .asDomainObject()
                .collect {
                    Log.i("CHECK", "refresh: $it")
                    insertLocation(it)
                }
        } catch (e: SocketTimeoutException) {
            Log.e("REFRESH", "REFRESH: ${e.stackTraceToString()}")
        }
    }
}