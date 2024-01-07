package com.example.skysnap.data.database.weather

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.skysnap.model.Weather

@Entity(tableName = "weather")
data class DbWeather(
    @PrimaryKey
    val location: String = "",
    val temp: Double = 0.00,
    val feelsLike: Double = 0.00,
    val tempMin: Double = 0.00,
    val tempMax: Double = 0.00
)

fun DbWeather.asDomainWeatherObject() : Weather {
    return Weather(
        location = this.location,
        temp = this.temp,
        feelsLike = this.feelsLike,
        tempMin = this.tempMin,
        tempMax = this.tempMax
    )
}

fun Weather.asDbWeatherObject() : DbWeather {
    return DbWeather(
        location = this.location,
        temp = this.temp,
        feelsLike = this.feelsLike,
        tempMin = this.tempMin,
        tempMax = this.tempMax
    )
}