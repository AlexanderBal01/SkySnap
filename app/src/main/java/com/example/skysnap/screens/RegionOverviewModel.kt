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
import com.example.skysnap.model.Region
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class RegionOverviewModel(private val regionRepository: RegionRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(RegionState(listOf<Region>()))
    val uiState: StateFlow<RegionState> = _uiState.asStateFlow()

    var regionApiState: RegionApiState by mutableStateOf(RegionApiState.Loading)
        private set
        init {
            getApiRegions()
        }

    private fun getApiRegions() {
        viewModelScope.launch {
            try {
                val listResult = regionRepository.getRegions()
                _uiState.update {
                    it.copy(currentRegionList = listResult)
                }
                regionApiState = RegionApiState.Success(listResult)
            } catch (e: IOException) {
                regionApiState = RegionApiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SkySnapApplication)
                val regionRepository = application.container.regionRepository
                RegionOverviewModel(regionRepository = regionRepository)
            }
        }
    }
}