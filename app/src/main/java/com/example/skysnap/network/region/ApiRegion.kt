package com.example.skysnap.network.region

import com.example.skysnap.model.Region
import kotlinx.serialization.Serializable

@Serializable
data class ApiRegion(val ID: String, val LocalizedName: String, val EnglishName: String) {}

fun List<ApiRegion>.asDomainObjects(): List<Region> {
    var domainList = this.map {
        Region(it.ID, it.LocalizedName, it.EnglishName)
    }
    return domainList
}