package com.example.skysnap.data.country.states

import com.example.skysnap.model.Country

data class CountryUiState(
    val countryId: String = "",
    val countryList: List<Country> = listOf(),
    val screenTitle: Int = 0,
    val regionId: String = "",
)
