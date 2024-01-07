package com.example.skysnap.ui.screens.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.skysnap.ui.screens.home.HomeScreen
import com.example.skysnap.ui.screens.home.HomeViewModel
import com.example.skysnap.ui.screens.weatherOverview.WeatherOverviewScreen
import com.example.skysnap.ui.screens.weatherOverview.WeatherOverviewViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun NavComponent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    fabActionVisible: Boolean = false,
    fabResetAction: () -> Unit = {},
    homeViewModel: HomeViewModel,
    weatherOverviewViewModel: WeatherOverviewViewModel
) {
    NavHost(
        navController = navController,
        startDestination = OverviewScreens.Home.name,
        modifier = modifier
    ) {
        composable(route = OverviewScreens.Home.name) {
            HomeScreen(homeViewModel = homeViewModel, navigateToWeather = { navController.navigate("${OverviewScreens.WeatherOverview.name}/{$it}") }, isAddingVisible = fabActionVisible, makeInvisable = fabResetAction)
        }
        composable(route = "${OverviewScreens.WeatherOverview.name}/{location}") {backstackEntry ->
            WeatherOverviewScreen(
                selectedLocation = backstackEntry.arguments?.getString("location").toString(),
                weatherOverviewViewModel = weatherOverviewViewModel,
            )
        }
    }
}