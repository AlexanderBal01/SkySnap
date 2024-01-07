package com.example.skysnap.network

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("data/2.5/weather")
    suspend fun getWeatherFromLocation(@Query("q") location: String, @Query("units") units: String, @Query("appid") apiKey: String) : ApiWeather
}

fun WeatherApiService.getWeatherAsFlow(location: String): Flow<ApiWeather> = flow {
    try {
        emit(getWeatherFromLocation(location, "metric", "546e06657d55ff096daf4bc0302bbf39"))
    } catch (e: Exception) {
        Log.e("API", "getWeatherAsFlow: " + e.stackTraceToString())
    }
}