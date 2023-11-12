package com.example.skysnap.network

import com.example.skysnap.model.Region

data class ApiRegion(val id: String, val name: String) {}

fun List<ApiRegion>.asDomainObjects(): List<Region> {
    var domainList = this.map {
        Region(it.id, it.name)
    }
    return domainList
}