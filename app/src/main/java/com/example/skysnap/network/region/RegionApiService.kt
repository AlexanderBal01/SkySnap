package com.example.skysnap.network.region

import retrofit2.http.GET
import retrofit2.http.Query

interface RegionApiService {
    @GET("locations/v1/regions")
    suspend fun getRegions(@Query("apikey") apikey: String, @Query("language") language: String): List<ApiRegion>
}