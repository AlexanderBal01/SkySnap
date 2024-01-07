package com.example.skysnap.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.skysnap.components.homeScreen.CreateLocation
import com.example.skysnap.components.homeScreen.LocationList
import com.example.skysnap.ui.screens.home.states.LocationApiState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
    navigateToWeather: () -> Unit,
    isAddingVisible: Boolean = false,
    makeInvisable: () -> Unit = {},
) {
    val locationOverviewState by homeViewModel.uiState.collectAsState()
    val locationListState by homeViewModel.uiListState.collectAsState()

    val locationApiState = homeViewModel.locationApiState

    val workerState by homeViewModel.wifiWorkerState.collectAsState()

    Column {
        Box(modifier = modifier) {
            when (locationApiState) {
                is LocationApiState.Loading -> Text("Loading...", color = Color.White)
                is LocationApiState.Error -> Text("Couldn't load", color = Color.White)
                is LocationApiState.Succes -> LocationList(navigateToWeather = navigateToWeather, locationListState = locationListState, locationOverviewState = locationOverviewState)
                else -> Text("error")
            }

            if (isAddingVisible) {
                CreateLocation(
                    locationName = locationOverviewState.newLocationName,
                    onLocationNameChanged = { homeViewModel.setNewLocationName(it) },
                    onLocationSaved = {
                        homeViewModel.addLocation()
                        makeInvisable()
                    },
                    onDismissRequest = { makeInvisable() },
                )
            }
        }
    }
}