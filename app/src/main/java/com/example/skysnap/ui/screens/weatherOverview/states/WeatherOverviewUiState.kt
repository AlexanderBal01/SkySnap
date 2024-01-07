package com.example.skysnap.ui.screens.weatherOverview.states

import androidx.work.WorkInfo
import com.example.skysnap.model.Location
import com.example.skysnap.model.Weather

/**
 * Represents the state of the UI for the Weather Overview screen.
 *
 * @param weather The weather information to display.
 * @param location The location information related to the weather.
 */
data class WeatherOverviewUiState(
    val weather: Weather? = null,
    val location: Location? = null
)

/**
 * Represents the state of the background worker for the Weather Overview screen.
 *
 * @param workerInfo Information about the background worker's state.
 */
data class WorkerState(val workerInfo: WorkInfo? = null)

/**
 * Represents the possible states of a weather-related API call.
 */
sealed interface WeatherApiState {
    /**
     * Represents a successful API call for weather information.
     */
    object Success: WeatherApiState

    /**
     * Represents an error in the API call for weather information.
     */
    object Error: WeatherApiState

    /**
     * Represents a loading state during the API call for weather information.
     */
    object Loading: WeatherApiState
}
