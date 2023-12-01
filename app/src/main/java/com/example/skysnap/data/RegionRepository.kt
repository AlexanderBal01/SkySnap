package com.example.skysnap.data

import android.util.Log
import com.example.skysnap.model.Country
import com.example.skysnap.model.Region
import com.example.skysnap.network.Region.RegionApiService
import com.example.skysnap.network.Region.asDomainObjects

interface RegionRepository {
    suspend fun getRegions() : List<Region>
    suspend fun getCountriesRegion(regionId: String): List<Country>
}

class ApiRegionRepository(
    private val regionApiService: RegionApiService
): RegionRepository {
    override suspend fun getRegions(): List<Region> {
        return regionApiService.getRegions("FxZbnUgAphCVmuwkP9oPpL5pR4mySqFN", "nl-be").asDomainObjects()
    }

    override suspend fun getCountriesRegion(regionId: String): List<Country> {
        Log.d("reg", regionId)
        return regionApiService.getCountriesRegion(id = regionId, "FxZbnUgAphCVmuwkP9oPpL5pR4mySqFN", "nl-be").asDomainObjects()
    }
}