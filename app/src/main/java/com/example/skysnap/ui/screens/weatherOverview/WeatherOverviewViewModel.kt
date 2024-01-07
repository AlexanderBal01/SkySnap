package com.example.skysnap.ui.screens.weatherOverview

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
import com.example.skysnap.data.WeatherRepository
import com.example.skysnap.model.Location
import com.example.skysnap.ui.screens.weatherOverview.states.WeatherApiState
import com.example.skysnap.ui.screens.weatherOverview.states.WeatherOverviewUiState
import com.example.skysnap.ui.screens.weatherOverview.states.WorkerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.io.IOException

/**
 * ViewModel for the Weather Overview screen, responsible for handling weather-related UI logic.
 *
 * @param weatherRepository Repository for accessing weather-related data.
 */
class WeatherOverviewViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    // MutableStateFlow for the UI state of the Weather Overview screen.
    private val _uiState = MutableStateFlow(WeatherOverviewUiState())
    val uiState: StateFlow<WeatherOverviewUiState> = _uiState.asStateFlow()

    // MutableState for the weather API state.
    var weatherApiState: WeatherApiState by mutableStateOf(WeatherApiState.Loading)
        private set

    // StateFlow for the background worker state.
    lateinit var wifiWorkerState: StateFlow<WorkerState>

    /**
     * Fetch weather information for the specified location from the repository.
     *
     * @param location The location for which weather information is requested.
     */
    fun getRepoWeather(location: String) {
        try {
            // Fetch weather information and update the UI state.
            weatherRepository.getWeather(location).map { weatherResponse ->
                _uiState.update {
                    it.copy(
                        weather = weatherResponse
                    )
                }
            }

            // Set the weather API state to success.
            weatherApiState = WeatherApiState.Success

            // Fetch the background worker state and convert it to a StateFlow.
            wifiWorkerState = weatherRepository.wifiWorkInfo.map { WorkerState(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = WorkerState()
                )
        } catch (e: IOException) {
            // Set the weather API state to error if an exception occurs.
            weatherApiState = WeatherApiState.Error
        }
    }

    /**
     * Set the selected location in the UI state.
     *
     * @param selectedLocation The selected location for which weather information is displayed.
     */
    fun setLocation(selectedLocation: Location?) {
        _uiState.update {
            it.copy(
                location = selectedLocation
            )
        }
    }

    companion object {
        // Singleton instance of the WeatherOverviewViewModel.
        private var Instance: WeatherOverviewViewModel? = null

        // Factory for creating the ViewModel using AndroidViewModelFactory.
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                if (Instance == null) {
                    // Create the WeatherOverviewViewModel instance using the application's container.
                    val application = (this[APPLICATION_KEY] as SkySnapApplication)
                    val weatherRepository = application.container.weatherRepository
                    Instance = WeatherOverviewViewModel(weatherRepository = weatherRepository)
                }
                Instance!!
            }
        }
    }
}
