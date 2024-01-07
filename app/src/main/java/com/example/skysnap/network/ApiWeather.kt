package com.example.skysnap.network

import com.example.skysnap.model.Weather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable

@Serializable
data class ApiWeather(
    val cloudPct: Int,
    val temp: Int,
    val feelsLike: Int,
    val humidity: Int,
    val minTemp: Int,
    val maxTemp: Int,
    val windSpeed: Double,
    val windDegrees: Int,
    val sunrise: Long,
    val sunSet: Long,
)

fun Flow<ApiWeather>.asDomainObject(): Flow<Weather> {
    return map {
        it.asDomainObject()
    }
}

fun ApiWeather.asDomainObject(): Weather {
    return Weather(
        cloudPct = cloudPct,
        temp = temp,
        feelsLike = feelsLike,
        humidity = humidity,
        minTemp = minTemp,
        maxTemp = maxTemp,
        windSpeed = windSpeed,
        windDegrees = windDegrees,
        sunrise = sunrise,
        sunSet = sunSet
    )
}