package com.example.skysnap.ui.screens.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.skysnap.R

/**
 * Enumeration representing the screens in the app's overview navigation.
 *
 * @param title String resource ID for the screen title.
 * @param icon Image vector for the screen icon.
 */
enum class OverviewScreens(@StringRes val title: Int, val icon: ImageVector) {
    // Home screen with the title and home icon.
    Home(title = R.string.overview_home, icon = Icons.Filled.Home),

    // Weather Overview screen with the title and place icon.
    WeatherOverview(title = R.string.weather_overview, icon = Icons.Filled.Place),
}
