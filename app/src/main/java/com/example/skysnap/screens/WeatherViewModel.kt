package com.example.skysnap.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.skysnap.SkySnapApplication
import com.example.skysnap.data.RegionRepository
import com.example.skysnap.data.WeatherUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class WeatherViewModel(private val regionRepository: RegionRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    var regionApiState: RegionApiState by mutableStateOf(RegionApiState.Loading)
        private set
        init {
            getApiRegions()
        }

    var countryApiState: CountryApiState by mutableStateOf(CountryApiState.Loading)
        private set

        init {
            getApiCountries()
        }

    private fun getApiRegions() {
        viewModelScope.launch {
            try {
                val listResult = regionRepository.getRegions()
                _uiState.update {
                    it.copy(regionList = listResult)
                }
                regionApiState = RegionApiState.Success(listResult)
            } catch (e: IOException) {
                regionApiState = RegionApiState.Error
                println(e)
            }
        }
    }

    private fun getApiCountries() {
        viewModelScope.launch {
            try {
                val listResult = regionRepository.getCountriesRegion(uiState.value.regionId)
                _uiState.update {
                    it.copy(countryList = listResult)
                }
                countryApiState = CountryApiState.Succes(listResult)
            } catch (e: IOException) {
                countryApiState = CountryApiState.Error
                println(e)
            }
        }
    }

    fun setRegionId(id: String) {
        _uiState.update {
            it.copy(regionId = id)
        }
    }

    fun setCountryId(id: String) {
        _uiState.update {
            it.copy(countryId = id)
        }

    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SkySnapApplication)
                val regionRepository = application.container.regionRepository
                WeatherViewModel(regionRepository = regionRepository)
            }
        }
    }
}