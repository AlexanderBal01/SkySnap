package com.example.skysnap.network.country

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CountryApiService {
    @GET("locations/v1/countries/{regionId}")
    suspend fun getCountries(@Path("regionId") id: String, @Query("apikey") apikey: String, @Query("language") language: String): List<ApiCountry>
}