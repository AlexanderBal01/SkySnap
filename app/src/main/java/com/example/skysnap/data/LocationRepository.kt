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

/**
 * Repository interface for location data.
 */
interface LocationRepository {
    /**
     * Get location data for a specific location as a flow.
     *
     * @param location The name or label for the location.
     * @return Flow emitting [Location] data for the specified location.
     */
    fun getLocation(location: String): Flow<Location?>

    /**
     * Get all location data as a flow.
     *
     * @return Flow emitting a list of [Location] data.
     */
    fun getAll(): Flow<List<Location>>

    /**
     * Insert location data into the repository.
     *
     * @param location The location data to be inserted.
     */
    suspend fun insertLocation(location: Location)

    /**
     * Refresh location data for a specific location, updating the repository.
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
 * Implementation of [LocationRepository] with caching capabilities.
 *
 * @param locationDao Data Access Object (DAO) for location data.
 * @param locationApiService API service for retrieving location data.
 * @param context Application context for accessing system services.
 */
class CachingLocationRepository(
    private val locationDao: LocationDao,
    private val locationApiService: LocationApiService,
    context: Context
) : LocationRepository {

    private var workId = UUID(1, 2)
    private val workManager = WorkManager.getInstance(context)

    /**
     * Flow providing [WorkInfo] for the Wi-Fi notification worker.
     */
    override var wifiWorkInfo: Flow<WorkInfo> =
        workManager.getWorkInfoByIdFlow(workId)

    /**
     * Get location data for a specific location as a flow.
     *
     * @param location The name or label for the location.
     * @return Flow emitting [Location] data for the specified location.
     */
    override fun getLocation(location: String): Flow<Location?> {
        return locationDao.getItem(location).map {
            it.asDomainLocationObject()
        }.onEach {
            refresh(location)
        }
    }

    /**
     * Get all location data as a flow.
     *
     * @return Flow emitting a list of [Location] data.
     */
    override fun getAll(): Flow<List<Location>> {
        return locationDao.getAllItems().map {
            it.asDomainLocationObjects()
        }
    }

    /**
     * Insert location data into the repository.
     *
     * @param location The location data to be inserted.
     */
    override suspend fun insertLocation(location: Location) {
        locationDao.insert(location.asDbLocationObject())
    }

    /**
     * Refresh location data for a specific location, updating the repository.
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
