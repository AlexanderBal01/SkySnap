package com.example.skysnap.network

import com.example.skysnap.model.Weather
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class ApiWeather(
    @SerializedName("main")
    val main: Map<String, Map<String, Double>>
)

fun Flow<ApiWeather>.asDomainObject(): Flow<Weather> {
    return map {
        it.asDomainObject()
    }
}

fun ApiWeather.asDomainObject(): Weather {
    return Weather(
        temp = main["main"]!!["temp"]!!.toDouble(),
        feelsLike = main["main"]!!["feels_like"]!!.toDouble(),
        tempMin = main["main"]!!["temp_min"]!!.toDouble(),
        tempMax = main["main"]!!["temp_max"]!!.toDouble(),

    )
}