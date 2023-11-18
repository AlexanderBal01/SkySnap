package com.example.skysnap.data

import com.example.skysnap.network.Region.RegionApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val regionRepository: RegionRepository
}

class DefaultAppContainer: AppContainer {
    private val baseUrl = "https://dataservice.accuweather.com/"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            Json.asConverterFactory("application/json".toMediaType())
        )
        .baseUrl(baseUrl)
        .build()

    private val retrofitService : RegionApiService by lazy {
        retrofit.create(RegionApiService::class.java)
    }

    override val regionRepository: RegionRepository by lazy {
        ApiRegionRepository(retrofitService)
    }
}