package com.example.skysnap.ui.screens.home.states

import androidx.work.WorkInfo
import com.example.skysnap.model.Location

/**
 * Represents the state of the Home screen.
 *
 * @param isAddingVisible Indicates whether the add location UI is visible.
 * @param newLocationName The name of the new location being added.
 * @param scrollActionIdx Index indicating the type of scrolling action.
 * @param scrollToItemIndex Index indicating the item to scroll to.
 * @param selectedLocation The selected location in the list.
 */
data class HomeState(
    val isAddingVisible: Boolean = false,
    val newLocationName: String = "",
    val scrollActionIdx: Int = 0,
    val scrollToItemIndex: Int = 0,
    val selectedLocation: Location? = null
)

/**
 * Represents the state of the location list.
 *
 * @param locationList The list of locations to display.
 */
data class LocationListState(val locationList: List<Location> = listOf())

/**
 * Represents the state of a background worker.
 *
 * @param workerInfo Information about the background worker's state.
 */
data class WorkerState(val workerInfo: WorkInfo? = null)

/**
 * Represents the possible states of a location-related API call.
 */
sealed interface LocationApiState {
    /**
     * Represents a successful API call.
     */
    object Success : LocationApiState

    /**
     * Represents an error in the API call.
     */
    object Error : LocationApiState

    /**
     * Represents a loading state during the API call.
     */
    object Loading : LocationApiState
}
