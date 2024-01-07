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

class HomeViewModel(private val locationRepository: LocationRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    lateinit var uiListState: StateFlow<LocationListState>

    var locationApiState: LocationApiState by mutableStateOf(LocationApiState.Loading)
        private set

    lateinit var wifiWorkerState: StateFlow<WorkerState>

    init {
        getRepoLocations()
    }

    private fun getRepoLocations() {
        try {
            uiListState = locationRepository.getAll().map { LocationListState(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = LocationListState()
                )
            locationApiState = LocationApiState.Succes

            wifiWorkerState = locationRepository.wifiWorkInfo.map { WorkerState(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = WorkerState()
                )
        } catch (e: IOException) {
            locationApiState = LocationApiState.Error
        }
    }

    fun setNewLocationName(location: String) {
        _uiState.update {
            it.copy(newLocationName = location)
        }
    }

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

    private suspend fun saveLocation(location: String) {
        if (validateInput()) {
            locationRepository.refresh(location = location)
        }
    }

    private fun validateInput(): Boolean {
        return with(_uiState) {
            value.newLocationName.isNotEmpty()
        }
    }

    companion object {
        private var Instance: HomeViewModel? = null
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                if (Instance == null) {
                    val application = (this[APPLICATION_KEY] as SkySnapApplication)
                    val locationRepository = application.container.locationRepository
                    Instance = HomeViewModel(locationRepository = locationRepository)
                }
                Instance!!
            }

        }
    }
}