package com.example.skysnap.data.database.location

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.skysnap.model.Location

@Entity(tableName = "location")
data class DbLocation(
    @PrimaryKey
    val name: String = "",
    val lat: Double = 0.00,
    val lon: Double = 0.00
)

fun DbLocation.asDomainLocationObject(): Location {
    return Location(
        name = this.name,
        lat = this.lat,
        lon = this.lon,
    )
}

fun Location.asDbLocationObject(): DbLocation {
    return DbLocation(
        name = this.name,
        lat = this.lat,
        lon = this.lon
    )
}

fun List<DbLocation>.asDomainLocationObjects(): List<Location> {
    var locationList = this.map {
        Location(
            name = it.name,
            lat = it.lat,
            lon = it.lon
        )
    }

    return locationList
}