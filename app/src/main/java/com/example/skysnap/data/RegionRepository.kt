package com.example.skysnap.data

import com.example.skysnap.model.Region
import com.example.skysnap.network.Region.RegionApiService
import com.example.skysnap.network.Region.asDomainObjects

interface RegionRepository {
    suspend fun getRegions() : List<Region>
}

class ApiRegionRepository(
    private val regionApiService: RegionApiService
): RegionRepository {
    override suspend fun getRegions(): List<Region> {
        return regionApiService.getRegions("FxZbnUgAphCVmuwkP9oPpL5pR4mySqFN", "nl-be").asDomainObjects()
    }
}