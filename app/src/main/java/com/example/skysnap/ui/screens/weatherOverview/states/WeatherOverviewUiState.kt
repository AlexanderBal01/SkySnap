package com.example.skysnap.ui.screens.weatherOverview.states

import androidx.work.WorkInfo
import com.example.skysnap.model.Location
import com.example.skysnap.model.Weather

data class WeatherOverviewUiState(
    val weather: Weather? = null,
    val location: Location? = null
)

data class WorkerState(val workerInfo: WorkInfo? = null)

sealed interface WeatherApiState {
    object Success: WeatherApiState
    object Error: WeatherApiState
    object Loading: WeatherApiState
}