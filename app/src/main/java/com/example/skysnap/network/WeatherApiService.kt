package com.example.skysnap.network

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit service interface for weather API.
 */
interface WeatherApiService {

    /**
     * Retrofit GET request to retrieve weather data from a location.
     *
     * @param location The location for which weather data is requested.
     * @param units The units for temperature measurement.
     * @param apiKey The API key for authentication.
     * @return [ApiWeather] representing the weather data.
     */
    @GET("data/2.5/weather")
    suspend fun getWeatherFromLocation(
        @Query("q") location: String,
        @Query("units") units: String,
        @Query("appid") apiKey: String
    ): ApiWeather
}

/**
 * Extension function to convert a weather API call to a flow of [ApiWeather].
 *
 * @param location The location for which weather data is requested.
 * @return Flow of [ApiWeather] objects.
 */
fun WeatherApiService.getWeatherAsFlow(location: String): Flow<ApiWeather> = flow {
    try {
        // Emit the weather data received from the API call
        emit(getWeatherFromLocation(location, "metric", "546e06657d55ff096daf4bc0302bbf39"))
    } catch (e: Exception) {
        // Log any exceptions that occur during the API call
        Log.e("API", "getWeatherAsFlow: " + e.stackTraceToString())
    }
}
