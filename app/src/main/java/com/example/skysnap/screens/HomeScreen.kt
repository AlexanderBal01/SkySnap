package com.example.skysnap.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.skysnap.components.RegionList
import com.example.skysnap.model.Region

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onRegionItemClicked: (String) -> Unit,
    weatherViewModel: WeatherViewModel = viewModel(factory = WeatherViewModel.Factory)
) {
    val regionState = weatherViewModel.uiState.collectAsState()
    val regionApiState = weatherViewModel.regionApiState

    Column(modifier = modifier.padding(start = 8.dp, end = 8.dp)) {
        when (regionApiState) {
            is RegionApiState.Loading -> Text("Loading...")
            is RegionApiState.Error -> Text("Couldn't load the api...")
            is RegionApiState.Success -> RegionList(regionOverviewState = regionState, onRegionItemClicked = onRegionItemClicked)
        }
    }
}