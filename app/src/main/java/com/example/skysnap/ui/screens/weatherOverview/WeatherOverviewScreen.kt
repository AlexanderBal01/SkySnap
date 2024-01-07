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

@Composable
fun WeatherOverviewScreen(
    modifier: Modifier = Modifier,
    weatherOverviewViewModel: WeatherOverviewViewModel,
    selectedLocation: String
) {
    weatherOverviewViewModel.getRepoWeather(selectedLocation)
    val uiStateWeather by weatherOverviewViewModel.uiState.collectAsState()

    when(weatherOverviewViewModel.weatherApiState) {
        is WeatherApiState.Loading -> Text(stringResource(R.string.loading), color = Color.White, fontSize = 45.sp)
        is WeatherApiState.Error -> Text(stringResource(R.string.could_not_load), color = Color.White, fontSize = 45.sp)
        is WeatherApiState.Success -> TodaySection(uiStateWeather = uiStateWeather)
        else -> Text(stringResource(R.string.could_not_load), color = Color.White, fontSize = 45.sp)
    }
}