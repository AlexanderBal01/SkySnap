package com.example.skysnap.data.database.weather

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.skysnap.model.Weather

@Entity(tableName = "weather")
data class DbWeather(
    @PrimaryKey
    val cityName: String = "",
    val cloudPct: Int = 0,
    val temp: Int = 0,
    val feelsLike: Int = 0,
    val humidity: Int = 0,
    val minTemp: Int = 0,
    val maxTemp: Int = 0,
    val windSpeed: Double = 0.00,
    val windDegrees: Int = 0,
    val sunrise: Long,
    val sunSet: Long,
)

fun DbWeather.asDomainWeatherObject(): Weather {
    return Weather(
        this.cityName,
        this.cloudPct,
        this.temp,
        this.feelsLike,
        this.humidity,
        this.minTemp,
        this.maxTemp,
        this.windSpeed,
        this.windDegrees,
        this.sunrise,
        this.sunSet,
    )
}

fun Weather.asDbWeatherObject(): DbWeather {
    return DbWeather(
        cityName = this.cityName,
        cloudPct = this.cloudPct,
        temp = this.temp,
        feelsLike = this.feelsLike,
        humidity = this.humidity,
        minTemp = this.minTemp,
        maxTemp = this.maxTemp,
        windSpeed = this.windSpeed,
        windDegrees = this.windDegrees,
        sunrise = this.sunrise,
        sunSet = this.sunSet,
    )
}

fun List<DbWeather>.asDomainWeatherObjects() : List<Weather> {
    var weatherList = this.map {
        Weather(
            it.cityName,
            it.cloudPct,
            it.temp,
            it.feelsLike,
            it.humidity,
            it.minTemp,
            it.maxTemp,
            it.windSpeed,
            it.windDegrees,
            it.sunrise,
            it.sunSet,
        )
    }

    return weatherList
}