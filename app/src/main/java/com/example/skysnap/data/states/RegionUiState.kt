package com.example.skysnap.data.states

import com.example.skysnap.model.Region

data class RegionUiState(
    val regionId: String = "",
    val regionList: List<Region> = listOf(),
)
