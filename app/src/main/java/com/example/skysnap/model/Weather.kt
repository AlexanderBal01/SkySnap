package com.example.skysnap.model

data class Weather(
    var location: String = "",
    var temp: Double = 0.00,
    var feelsLike: Double = 0.00,
    var tempMin: Double = 0.00,
    var tempMax: Double = 0.00
)
