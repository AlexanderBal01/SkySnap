package com.example.skysnap.network

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherApiService {
    @Headers("X-Api-Key: " + "9XetZjbjwPs8X7a9MfRxVA==RPdq2Q6XFUGOWQzO")
    @GET("weather")
    suspend fun getWeather(@Query("city") city: String): ApiWeather
}

fun WeatherApiService.getWeatherAsFlow(city: String): Flow<ApiWeather> = flow {
    try {
        emit(getWeather(city))
    } catch (e: Exception) {
        Log.e("API", "getWeatherAsFlow: " + e.stackTraceToString())
    }
}