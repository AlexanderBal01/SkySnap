package com.example.skysnap.network

import com.example.skysnap.model.Region

data class ApiRegion(val ID: String, val LocalizedName: String, val EnglishName: String) {}

fun List<ApiRegion>.asDomainObjects(): List<Region> {
    var domainList = this.map {
        Region(it.ID, it.LocalizedName, it.EnglishName)
    }
    return domainList
}