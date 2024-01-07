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

class WeatherOverviewViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(WeatherOverviewUiState())
    val uiState: StateFlow<WeatherOverviewUiState> = _uiState.asStateFlow()

    var weatherApiState: WeatherApiState by mutableStateOf(WeatherApiState.Loading)
        private set

    lateinit var wifiWorkerState: StateFlow<WorkerState>

    fun getRepoWeather(location: String) {
            try {
                weatherRepository.getWeather(location).map { weatherResponse ->
                    _uiState.update {
                        it.copy(
                            weather = weatherResponse
                        )
                    }
                }

                weatherApiState = WeatherApiState.Success

                wifiWorkerState = weatherRepository.wifiWorkInfo.map { WorkerState(it) }
                    .stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(5_000L),
                        initialValue = WorkerState()
                    )
            } catch (e: IOException) {
                weatherApiState = WeatherApiState.Error
            }
    }

    fun setLocation(selectedLocation: Location?) {
        _uiState.update {
            it.copy(
                location = selectedLocation
            )
        }
    }

    companion object {
        private var Instance: WeatherOverviewViewModel? = null
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                if (Instance == null) {
                    val application = (this[APPLICATION_KEY] as SkySnapApplication)
                    val weatherRepository = application.container.weatherRepository
                    Instance = WeatherOverviewViewModel(weatherRepository = weatherRepository)
                }
                Instance!!
            }

        }
    }
}