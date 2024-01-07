package com.example.skysnap.data.database.location

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.skysnap.model.Location

/**
 * Room Entity class representing a location in the database.
 */
@Entity(tableName = "location")
data class DbLocation(
    @PrimaryKey
    val name: String = "",
    val lat: Double = 0.00,
    val lon: Double = 0.00
)

/**
 * Extension function to convert a DbLocation object to a Location object.
 *
 * @return Location object.
 */
fun DbLocation.asDomainLocationObject(): Location {
    return Location(
        name = this.name,
        lat = this.lat,
        lon = this.lon,
    )
}

/**
 * Extension function to convert a Location object to a DbLocation object.
 *
 * @return DbLocation object.
 */
fun Location.asDbLocationObject(): DbLocation {
    return DbLocation(
        name = this.name,
        lat = this.lat,
        lon = this.lon
    )
}

/**
 * Extension function to convert a list of DbLocation objects to a list of Location objects.
 *
 * @return List of Location objects.
 */
fun List<DbLocation>.asDomainLocationObjects(): List<Location> {
    return this.map {
        Location(
            name = it.name,
            lat = it.lat,
            lon = it.lon
        )
    }
}
