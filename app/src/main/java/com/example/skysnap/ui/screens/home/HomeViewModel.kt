package com.example.skysnap.ui.screens.home

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
import com.example.skysnap.data.LocationRepository
import com.example.skysnap.model.Location
import com.example.skysnap.ui.screens.home.states.HomeState
import com.example.skysnap.ui.screens.home.states.LocationApiState
import com.example.skysnap.ui.screens.home.states.LocationListState
import com.example.skysnap.ui.screens.home.states.WorkerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * ViewModel for the Home screen, responsible for handling UI-related logic.
 *
 * @param locationRepository Repository for accessing location-related data.
 */
class HomeViewModel(private val locationRepository: LocationRepository) : ViewModel() {

    // MutableStateFlow for the state of the Home screen.
    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    // StateFlow for the location list.
    lateinit var uiListState: StateFlow<LocationListState>

    // MutableState for the API state related to location data.
    var locationApiState: LocationApiState by mutableStateOf(LocationApiState.Loading)
        private set

    // StateFlow for the background worker state.
    lateinit var wifiWorkerState: StateFlow<WorkerState>

    init {
        // Initialize the HomeViewModel by fetching location data.
        getRepoLocations()
    }

    private fun getRepoLocations() {
        try {
            // Fetch the location list from the repository and convert it to a StateFlow.
            uiListState = locationRepository.getAll().map { LocationListState(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = LocationListState()
                )

            // Set the location API state to success.
            locationApiState = LocationApiState.Success

            // Fetch the background worker state from the repository and convert it to a StateFlow.
            wifiWorkerState = locationRepository.wifiWorkInfo.map { WorkerState(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = WorkerState()
                )
        } catch (e: IOException) {
            // If an exception occurs while fetching data, set the location API state to error.
            locationApiState = LocationApiState.Error
        }
    }

    // Update the new location name in the UI state.
    fun setNewLocationName(location: String) {
        _uiState.update {
            it.copy(newLocationName = location)
        }
    }

    // Add a new location to the repository.
    fun addLocation() {
        viewModelScope.launch { saveLocation(_uiState.value.newLocationName) }

        _uiState.update {
            it.copy(
                newLocationName = "",
                scrollActionIdx = it.scrollActionIdx.plus(1),
                scrollToItemIndex = uiListState.value.locationList.size
            )
        }
    }

    // Save the location to the repository if it passes validation.
    private suspend fun saveLocation(location: String) {
        if (validateInput()) {
            locationRepository.refresh(location = location)
        }
    }

    // Validate the input for adding a new location.
    private fun validateInput(): Boolean {
        return with(_uiState) {
            value.newLocationName.isNotEmpty()
        }
    }

    // Set the selected location in the UI state.
    fun setSelectedLocation(location: String) {
        locationRepository.getLocation(location).map { locationResponse ->
            _uiState.update { state ->
                state.copy(
                    selectedLocation = locationResponse
                )
            }
        }
    }

    // Get the selected location from the UI state.
    fun getSelectedLocation(): Location {
        return Location(
            name = _uiState.value.selectedLocation!!.name,
            lat = _uiState.value.selectedLocation!!.lat,
            lon = _uiState.value.selectedLocation!!.lon,
        )
    }

    companion object {
        // Singleton instance of the HomeViewModel.
        private var Instance: HomeViewModel? = null

        // Factory for creating the ViewModel using AndroidViewModelFactory.
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                if (Instance == null) {
                    // Create the HomeViewModel instance using the application's container.
                    val application = (this[APPLICATION_KEY] as SkySnapApplication)
                    val locationRepository = application.container.locationRepository
                    Instance = HomeViewModel(locationRepository = locationRepository)
                }
                Instance!!
            }
        }
    }
}
