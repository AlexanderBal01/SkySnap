package com.example.skysnap.network.Region

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RegionApiService {
    @GET("locations/v1/regions")
    suspend fun getRegions(@Query("apikey") apikey: String, @Query("language") language: String): List<ApiRegion>

    @GET("locations/v1locations/v1/countries/{countryId}")
    suspend fun getCountriesRegion(@Path("countryId") id: String, @Query("apikey") apikey: String, @Query("language") language: String): List<ApiCountry>
}