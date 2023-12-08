package com.example.skysnap.network.country

import com.example.skysnap.model.Country
import kotlinx.serialization.Serializable

@Serializable
data class ApiCountry(val ID: String, val LocalizedName: String, val EnglishName: String) {}

fun List<ApiCountry>.asDomainObjects(): List<Country> {
    var domainList = this.map {
        Country(it.ID, it.LocalizedName, it.EnglishName)
    }
    return domainList
}