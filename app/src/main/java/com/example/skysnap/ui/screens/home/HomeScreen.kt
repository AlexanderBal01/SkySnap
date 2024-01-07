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

/**
 * Composable function representing the Home screen of the application.
 *
 * @param modifier Modifier for styling the layout.
 * @param homeViewModel ViewModel providing the data for the Home screen.
 * @param navigateToWeather Callback to navigate to the weather screen.
 * @param isAddingVisible Boolean indicating whether the add location UI is visible.
 * @param makeInvisible Callback to make the add location UI invisible.
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
    navigateToWeather: (String) -> Unit,
    isAddingVisible: Boolean = false,
    makeInvisible: () -> Unit = {},
) {
    // Collect the current state of the location overview.
    val locationOverviewState by homeViewModel.uiState.collectAsState()

    // Collect the current state of the location list.
    val locationListState by homeViewModel.uiListState.collectAsState()

    // Get the current state of the location API call.
    val locationApiState = homeViewModel.locationApiState

    // Collect the current state of the background worker.
    val workerState by homeViewModel.wifiWorkerState.collectAsState()

    // Compose the UI layout using Jetpack Compose components.
    Column {
        Box(modifier = modifier) {
            // Show loading text if the location data is still loading.
            when (locationApiState) {
                is LocationApiState.Loading ->
                    Text(stringResource(R.string.loading), color = Color.White, fontSize = 45.sp)
                // Show error text if there's an issue with the location API call.
                is LocationApiState.Error ->
                    Text(stringResource(R.string.could_not_load), color = Color.White, fontSize = 45.sp)
                // Display the location list if the API call is successful.
                is LocationApiState.Success ->
                    LocationList(
                        navigateToWeather = navigateToWeather,
                        locationListState = locationListState,
                        locationOverviewState = locationOverviewState,
                        homeViewModel = homeViewModel
                    )
                // Show default text for other cases.
                else ->
                    Text(stringResource(R.string.could_not_load), color = Color.White, fontSize = 45.sp)
            }

            // Display the add location UI if it's visible.
            if (isAddingVisible) {
                CreateLocation(
                    locationName = locationOverviewState.newLocationName,
                    onLocationNameChanged = { homeViewModel.setNewLocationName(it) },
                    onLocationSaved = {
                        homeViewModel.addLocation()
                        makeInvisible()
                    },
                    onDismissRequest = { makeInvisible() },
                )
            }
        }
    }
}
