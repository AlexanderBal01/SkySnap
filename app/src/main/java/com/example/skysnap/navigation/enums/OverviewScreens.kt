package com.example.skysnap.navigation.enums

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.skysnap.R

enum class OverviewScreens(@StringRes val title: Int, val icon: ImageVector) {
    SkySnap(title = R.string.app_name, icon = Icons.Filled.Home),
    Home(title = R.string.home, icon = Icons.Filled.Home),
    Favourites(title = R.string.favourites, icon = Icons.Filled.Favorite)
}