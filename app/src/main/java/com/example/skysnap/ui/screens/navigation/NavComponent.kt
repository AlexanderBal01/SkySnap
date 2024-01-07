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

/**
 * Composable function representing the navigation component of the app.
 *
 * @param navController Navigation controller for handling navigation events.
 * @param modifier Modifier for styling the layout.
 * @param fabActionVisible Boolean indicating whether the FAB action is visible.
 * @param fabResetAction Callback to reset the FAB action.
 * @param homeViewModel ViewModel for the Home screen.
 * @param weatherOverviewViewModel ViewModel for the Weather Overview screen.
 */
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
    // Set up the navigation host with Compose.
    NavHost(
        navController = navController,
        startDestination = OverviewScreens.Home.name,
        modifier = modifier
    ) {
        // Define the composable for the Home screen.
        composable(route = OverviewScreens.Home.name) {
            HomeScreen(
                homeViewModel = homeViewModel,
                // Navigate to the Weather Overview screen with the selected location.
                navigateToWeather = { navController.navigate("${OverviewScreens.WeatherOverview.name}/{$it}") },
                isAddingVisible = fabActionVisible,
                makeInvisible = fabResetAction
            )
        }

        // Define the composable for the Weather Overview screen.
        composable(route = "${OverviewScreens.WeatherOverview.name}/{location}") { backstackEntry ->
            WeatherOverviewScreen(
                selectedLocation = backstackEntry.arguments?.getString("location").toString(),
                weatherOverviewViewModel = weatherOverviewViewModel,
            )
        }
    }
}
