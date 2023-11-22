package com.example.skysnap.screens

import com.example.skysnap.model.Region

sealed interface RegionApiState {
    data class Success(val regions: List<Region>) : RegionApiState
    object Error: RegionApiState
    object Loading: RegionApiState
}
