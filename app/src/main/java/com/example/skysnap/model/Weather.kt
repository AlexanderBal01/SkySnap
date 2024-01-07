package com.example.skysnap.model

data class Weather(
    var cityName: String = "",
    var cloudPct: Int,
    var temp: Int,
    var feelsLike: Int,
    var humidity: Int,
    var minTemp: Int,
    var maxTemp: Int,
    var windSpeed: Double,
    var windDegrees: Int,
    var sunrise: Long,
    var sunSet: Long,
)
