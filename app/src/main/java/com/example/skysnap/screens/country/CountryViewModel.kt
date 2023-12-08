package com.example.skysnap.screens.country

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
import com.example.skysnap.data.country.CountryRepository
import com.example.skysnap.data.country.states.CountryUiState
import com.example.skysnap.screens.CountryApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class CountryViewModel(private val countryRepository: CountryRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(CountryUiState())
    val uiState: StateFlow<CountryUiState> = _uiState.asStateFlow()

    var countryApiState: CountryApiState by mutableStateOf(CountryApiState.Loading)
        private set


    fun getApiCountries(id: String) {
        viewModelScope.launch {
            try {
                val listResult = countryRepository.getCountries(id)
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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SkySnapApplication)
                val countryRepository = application.container.countryRepository
                CountryViewModel(countryRepository)
            }
        }
    }
}