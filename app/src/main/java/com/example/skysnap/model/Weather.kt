package com.example.skysnap.model

/**
 * Data class representing weather information for a specific location.
 *
 * @param location The name or label for the location associated with the weather data.
 * @param temp The current temperature in Celsius.
 * @param feelsLike The "feels like" temperature, indicating how the weather feels to a person.
 * @param tempMin The minimum temperature in Celsius for the given time period.
 * @param tempMax The maximum temperature in Celsius for the given time period.
 */
data class Weather(
    var location: String = "",
    var temp: Double = 0.00,
    var feelsLike: Double = 0.00,
    var tempMin: Double = 0.00,
    var tempMax: Double = 0.00
)
