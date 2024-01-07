package com.example.skysnap.ui.screens.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.skysnap.R

enum class OverviewScreens(@StringRes val title: Int, val icon: ImageVector) {
    Home(title = R.string.overview_home, icon = Icons.Filled.Home),
    AppInfo(title= R.string.overview_info, icon = Icons.Filled.Info),
    WeatherOverview(title = R.string.weather_overview, icon = Icons.Filled.Place),
    Starred(title = R.string.overview_starred, icon = Icons.Filled.Star)
}