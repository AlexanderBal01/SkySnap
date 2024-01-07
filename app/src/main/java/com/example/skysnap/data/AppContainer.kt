package com.example.skysnap.data

import android.content.Context
import com.example.skysnap.data.database.WeatherDb
import com.example.skysnap.network.LocationApiService
import com.example.skysnap.network.NetworkConnectionInterceptor
import com.example.skysnap.network.WeatherApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val locationRepository: LocationRepository
    val weatherRepository: WeatherRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    private val networkCheck = NetworkConnectionInterceptor(context)
    private val client = OkHttpClient.Builder()
        .addInterceptor(networkCheck)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val baseUrl = "http://api.openweathermap.org/"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            GsonConverterFactory.create(),
        )
        .baseUrl(baseUrl)
        .client(client)
        .build()

    private val retrofitLocationService: LocationApiService by lazy {
        retrofit.create(LocationApiService::class.java)
    }

    private val retrofitWeatherService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }

    override val locationRepository: LocationRepository by lazy {
        CachingLocationRepository(WeatherDb.getDatabase(context = context).locationDao(), retrofitLocationService, context)
    }

    override val weatherRepository: WeatherRepository by lazy {
        CachingWeatherRepository(WeatherDb.getDatabase(context = context).weatherDao(), retrofitWeatherService, context)
    }
}