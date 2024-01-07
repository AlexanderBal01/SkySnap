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

/**
 * Interface defining the dependencies needed for the application.
 */
interface AppContainer {
    val locationRepository: LocationRepository
    val weatherRepository: WeatherRepository
}

/**
 * Default implementation of [AppContainer] providing instances of repositories and services.
 *
 * @param context Application context for accessing system services.
 */
class DefaultAppContainer(private val context: Context) : AppContainer {

    // Network interceptor for checking network connectivity
    private val networkCheck = NetworkConnectionInterceptor(context)

    // OkHttpClient with network interceptor and logging interceptor
    private val client = OkHttpClient.Builder()
        .addInterceptor(networkCheck)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    // Base URL for the OpenWeatherMap API
    private val baseUrl = "http://api.openweathermap.org/"

    // Retrofit instance for making network requests
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(client)
        .build()

    // Lazily initialized Retrofit services for location and weather APIs
    private val retrofitLocationService: LocationApiService by lazy {
        retrofit.create(LocationApiService::class.java)
    }

    private val retrofitWeatherService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }

    // Lazily initialized instances of location and weather repositories
    override val locationRepository: LocationRepository by lazy {
        CachingLocationRepository(
            WeatherDb.getDatabase(context = context).locationDao(),
            retrofitLocationService,
            context
        )
    }

    override val weatherRepository: WeatherRepository by lazy {
        CachingWeatherRepository(
            WeatherDb.getDatabase(context = context).weatherDao(),
            retrofitWeatherService,
            context
        )
    }
}
