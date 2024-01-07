package com.example.skysnap.network

import com.example.skysnap.model.Location
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class ApiLocation(
    @SerializedName("name")
    val name: String,
    @SerializedName("local_names")
    val localNames: Map<String, String>,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("country")
    val country: String,
    @SerializedName("state")
    val sate: String,
)

fun Flow<List<ApiLocation>>.asDomainObject(): Flow<Location> {
    return map {
        it.first().asDomainObject()
    }
}

fun ApiLocation.asDomainObject(): Location {
    return Location(
        name = this.name,
        lat = this.lat,
        lon = this.lon
    )
}