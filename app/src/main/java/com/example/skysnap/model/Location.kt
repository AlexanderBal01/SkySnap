package com.example.skysnap.model

/**
 * Data class representing a geographic location.
 *
 * @param name The name or label for the location.
 * @param lat The latitude of the location.
 * @param lon The longitude of the location.
 */
data class Location(
    var name: String = "",
    var lat: Double,
    var lon: Double,
)
