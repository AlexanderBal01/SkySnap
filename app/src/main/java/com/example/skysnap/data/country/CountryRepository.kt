package com.example.skysnap.data.country

import com.example.skysnap.model.Country
import com.example.skysnap.network.country.CountryApiService
import com.example.skysnap.network.country.asDomainObjects

interface CountryRepository {
    suspend fun getCountries(regionId: String) : List<Country>
}

class ApiCountryRepository(
    private val countryApiService: CountryApiService
): CountryRepository {
    override suspend fun getCountries(regionId: String): List<Country> {
        return countryApiService.getCountries(id = regionId, apikey = "FxZbnUgAphCVmuwkP9oPpL5pR4mySqFN", language = "nl-be").asDomainObjects()
    }
}