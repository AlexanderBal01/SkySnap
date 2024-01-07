package com.example.skysnap.ui.screens.home.states

import androidx.work.WorkInfo
import com.example.skysnap.model.Location

data class HomeState(
    val isAddingVisible: Boolean = false,
    val newLocationName: String = "",
    val scrollActionIdx: Int = 0,
    val scrollToItemIndex: Int = 0,
)

data class LocationListState(val locationList: List<Location> = listOf())

data class WorkerState(val workerInfo: WorkInfo? = null)

sealed interface LocationApiState {
    object Succes: LocationApiState
    object Error : LocationApiState
    object Loading : LocationApiState
}