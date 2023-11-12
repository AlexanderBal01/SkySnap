package com.example.skysnap.data

import com.example.skysnap.model.Region
import com.example.skysnap.network.RegionApiService
import com.example.skysnap.network.asDomainObjects

interface RegionRepository {
    suspend fun getRegions() : List<Region>
}

class ApiRegionRepository(
    private val regionApiService: RegionApiService
): RegionRepository {
    override suspend fun getRegions(): List<Region> {
        return regionApiService.getRegions().asDomainObjects()
    }
}