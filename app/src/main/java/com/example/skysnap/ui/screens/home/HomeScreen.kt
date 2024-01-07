package com.example.skysnap.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.skysnap.R
import com.example.skysnap.components.homeScreen.CreateLocation
import com.example.skysnap.components.homeScreen.LocationList
import com.example.skysnap.ui.screens.home.states.LocationApiState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
    navigateToWeather: (String) -> Unit,
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
                is LocationApiState.Loading -> Text(stringResource(R.string.loading), color = Color.White, fontSize = 45.sp)
                is LocationApiState.Error -> Text(stringResource(R.string.could_not_load), color = Color.White, fontSize = 45.sp)
                is LocationApiState.Success -> LocationList(navigateToWeather = navigateToWeather, locationListState = locationListState, locationOverviewState = locationOverviewState, homeViewModel = homeViewModel)
                else -> Text(stringResource(R.string.could_not_load), color = Color.White, fontSize = 45.sp)
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