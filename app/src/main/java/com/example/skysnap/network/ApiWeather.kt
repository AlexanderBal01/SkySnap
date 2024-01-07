package com.example.skysnap.network

import com.example.skysnap.model.Weather
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Data class representing weather data received from the API.
 *
 * @param main Map containing main weather information.
 */
data class ApiWeather(
    @SerializedName("main")
    val main: Map<String, Map<String, Double>>
)

/**
 * Extension function to convert a flow of [ApiWeather] to a flow of [Weather].
 *
 * @return Flow of [Weather] objects.
 */
fun Flow<ApiWeather>.asDomainObject(): Flow<Weather> {
    return map {
        it.asDomainObject()
    }
}

/**
 * Extension function to convert an [ApiWeather] to a [Weather].
 *
 * @return [Weather] object.
 */
fun ApiWeather.asDomainObject(): Weather {
    return Weather(
        temp = main["main"]!!["temp"]!!.toDouble(),
        feelsLike = main["main"]!!["feels_like"]!!.toDouble(),
        tempMin = main["main"]!!["temp_min"]!!.toDouble(),
        tempMax = main["main"]!!["temp_max"]!!.toDouble()
    )
}
