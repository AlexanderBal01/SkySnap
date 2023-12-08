package com.example.skysnap.data.region.states

import com.example.skysnap.model.Region

data class RegionUiState(
    val regionId: String = "",
    val regionList: List<Region> = listOf(),
    val screenTitle: Int = 0,
)
