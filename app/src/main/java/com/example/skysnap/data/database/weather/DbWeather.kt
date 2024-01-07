package com.example.skysnap.data.database.weather

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.skysnap.model.Weather

/**
 * Room Entity class representing weather data in the database.
 *
 * @param location The location for which the weather data is stored (primary key).
 * @param temp The temperature.
 * @param feelsLike The "feels like" temperature.
 * @param tempMin The minimum temperature.
 * @param tempMax The maximum temperature.
 */
@Entity(tableName = "weather")
data class DbWeather(
    @PrimaryKey
    val location: String = "",
    val temp: Double = 0.00,
    val feelsLike: Double = 0.00,
    val tempMin: Double = 0.00,
    val tempMax: Double = 0.00
)

/**
 * Extension function to map DbWeather to the domain Weather object.
 *
 * @return Weather object.
 */
fun DbWeather.asDomainWeatherObject(): Weather {
    return Weather(
        location = this.location,
        temp = this.temp,
        feelsLike = this.feelsLike,
        tempMin = this.tempMin,
        tempMax = this.tempMax
    )
}

/**
 * Extension function to map Weather to the database DbWeather object.
 *
 * @return DbWeather object.
 */
fun Weather.asDbWeatherObject(): DbWeather {
    return DbWeather(
        location = this.location,
        temp = this.temp,
        feelsLike = this.feelsLike,
        tempMin = this.tempMin,
        tempMax = this.tempMax
    )
}
