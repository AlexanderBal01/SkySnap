package com.example.skysnap.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.skysnap.ui.screens.home.HomeScreen
import com.example.skysnap.ui.screens.home.HomeViewModel

@Composable
fun NavComponent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    fabActionVisible: Boolean = false,
    fabResetAction: () -> Unit = {},
    homeViewModel: HomeViewModel,
) {
    val navigateToWeatherScreen = {
        navController.navigate(OverviewScreens.WeatherOverview.name)
    }

    NavHost(
        navController = navController,
        startDestination = OverviewScreens.Home.name,
        modifier = modifier
    ) {
        composable(route = OverviewScreens.Home.name) {
            HomeScreen(homeViewModel = homeViewModel, navigateToWeather = navigateToWeatherScreen, isAddingVisible = fabActionVisible, makeInvisable = fabResetAction)
        }
        composable(route = OverviewScreens.WeatherOverview.name) {

        }
    }
}