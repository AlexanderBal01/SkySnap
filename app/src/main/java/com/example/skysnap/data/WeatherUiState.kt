package com.example.skysnap.data

import com.example.skysnap.model.Country
import com.example.skysnap.model.Region

data class WeatherUiState(
    val regionId: String = "",
    val countryId: String = "",
    val regionList: List<Region> = listOf(),
    val CountryList: List<Country> = listOf(),
)
