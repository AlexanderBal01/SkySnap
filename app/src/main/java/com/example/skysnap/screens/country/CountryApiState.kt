package com.example.skysnap.screens.country

import com.example.skysnap.model.Country

sealed interface CountryApiState {
    data class Succes(val countries: List<Country>) : CountryApiState
    object Error: CountryApiState
    object Loading: CountryApiState
}