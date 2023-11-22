package com.example.skysnap.network.Region

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RegionApiService {
    @GET("locations/v1/regions")
    suspend fun getRegions(@Query("apikey") apikey: String, @Query("language") language: String): List<ApiRegion>

    @GET("locations/v1/countries/{regionId}")
    suspend fun getCountriesRegion(@Path("regionId") id: String, @Query("apikey") apikey: String, @Query("language") language: String): List<ApiCountry>
}