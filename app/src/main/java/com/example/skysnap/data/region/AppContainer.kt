package com.example.skysnap.data.region

import com.example.skysnap.data.country.ApiCountryRepository
import com.example.skysnap.data.country.CountryRepository
import com.example.skysnap.network.country.CountryApiService
import com.example.skysnap.network.region.RegionApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create

interface AppContainer {
    val regionRepository: RegionRepository
    val countryRepository: CountryRepository
}

class DefaultAppContainer: AppContainer {
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()


    private val baseUrl = "https://dataservice.accuweather.com/"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            Json.asConverterFactory("application/json".toMediaType())
        )
        .baseUrl(baseUrl)
        .client(httpClient)
        .build()

    private val retrofitServiceRegion : RegionApiService by lazy {
        retrofit.create(RegionApiService::class.java)
    }

    private val retrofitServiceCountry : CountryApiService by lazy {
        retrofit.create(CountryApiService::class.java)
    }

    override val regionRepository: RegionRepository by lazy {
        ApiRegionRepository(retrofitServiceRegion)
    }

    override val countryRepository: CountryRepository by lazy {
        ApiCountryRepository(retrofitServiceCountry)
    }
}