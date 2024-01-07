package com.example.skysnap.network

import com.example.skysnap.model.Location
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Data class representing the location data received from the API.
 *
 * @param name The name of the location.
 * @param lat The latitude coordinate of the location.
 * @param lon The longitude coordinate of the location.
 */
data class ApiLocation(
    @SerializedName("name")
    val name: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,
)

/**
 * Extension function to convert a flow of lists of [ApiLocation] to a flow of [Location].
 *
 * @return Flow of [Location] objects.
 */
fun Flow<List<ApiLocation>>.asDomainObject(): Flow<Location> {
    return map {
        it.first().asDomainObject()
    }
}

/**
 * Extension function to convert an [ApiLocation] to a [Location].
 *
 * @return [Location] object.
 */
fun ApiLocation.asDomainObject(): Location {
    return Location(
        name = this.name,
        lat = this.lat,
        lon = this.lon
    )
}
