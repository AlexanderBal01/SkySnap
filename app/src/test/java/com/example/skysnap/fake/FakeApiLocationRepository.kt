package com.example.skysnap.fake

import androidx.work.WorkInfo
import com.example.skysnap.data.LocationRepository
import com.example.skysnap.model.Location
import kotlinx.coroutines.flow.Flow

class FakeApiLocationRepository : LocationRepository {
    override fun getLocation(location: String): Flow<Location?> {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<List<Location>> {
        return
    }

    override suspend fun insertLocation(location: Location) {
        TODO("Not yet implemented")
    }

    override suspend fun refresh(location: String) {
        TODO("Not yet implemented")
    }

    override var wifiWorkInfo: Flow<WorkInfo>
        get() = TODO("Not yet implemented")
        set(value) {}
}