package com.example.skysnap.ui.screens.weatherOverview

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.skysnap.R
import com.example.skysnap.components.weatherOverviewScreen.TodaySection
import com.example.skysnap.ui.screens.weatherOverview.states.WeatherApiState

/**
 * Composable function representing the Weather Overview screen of the application.
 *
 * @param modifier Modifier for styling the layout.
 * @param weatherOverviewViewModel ViewModel providing the data for the Weather Overview screen.
 * @param selectedLocation The selected location for which weather information is displayed.
 */
@Composable
fun WeatherOverviewScreen(
    modifier: Modifier = Modifier,
    weatherOverviewViewModel: WeatherOverviewViewModel,
    selectedLocation: String
) {
    // Fetch weather information for the selected location.
    weatherOverviewViewModel.getRepoWeather(selectedLocation)

    // Collect the current UI state of the weather information.
    val uiStateWeather by weatherOverviewViewModel.uiState.collectAsState()

    // Determine the UI to display based on the weather API state.
    when(weatherOverviewViewModel.weatherApiState) {
        // Show loading text if weather data is still loading.
        is WeatherApiState.Loading ->
            Text(stringResource(R.string.loading), color = Color.White, fontSize = 45.sp)

        // Show error text if there's an issue with the weather API call.
        is WeatherApiState.Error ->
            Text(stringResource(R.string.could_not_load), color = Color.White, fontSize = 45.sp)

        // Display the Today section if the API call is successful.
        is WeatherApiState.Success ->
            TodaySection(uiStateWeather = uiStateWeather)

        // Show default text for other cases.
        else ->
            Text(stringResource(R.string.could_not_load), color = Color.White, fontSize = 45.sp)
    }
}