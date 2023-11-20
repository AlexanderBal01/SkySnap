package com.example.skysnap

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.skysnap.components.SkySnapAppBar
import com.example.skysnap.components.SkySnapBottomAppBar
import com.example.skysnap.screens.AppInfoScreen
import com.example.skysnap.screens.CountryScreen
import com.example.skysnap.screens.HomeScreen
import com.example.skysnap.screens.OverviewScreens
import com.example.skysnap.screens.StarredScreen
import com.example.skysnap.screens.WeatherViewModel
import com.example.skysnap.ui.theme.SkySnapTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkySnapApp(
    weatherViewModel: WeatherViewModel = viewModel(factory = WeatherViewModel.Factory)
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()

    val canNavigateBack = navController.previousBackStackEntry != null
    val navigateUp:() -> Unit = { navController.navigateUp() }
    val goHome: () -> Unit = {
        navController.popBackStack(
            OverviewScreens.Start.name,
            inclusive = false
        )
    }
    val goToAppInfo = { navController.navigate(OverviewScreens.AppInfo.name) }
    val goToStarred = { navController.navigate(OverviewScreens.Starred.name) }

    val currentScreenTitle = OverviewScreens.valueOf(
        backStackEntry?.destination?.route ?: OverviewScreens.Start.name,
    ).title

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            SkySnapAppBar(
                canNavigateBack = canNavigateBack,
                navigateUp = navigateUp,
                currentScreenTitle = currentScreenTitle,
            )
        },
        bottomBar = {
            SkySnapBottomAppBar(
                goHome,
                goToAppInfo,
                goToStarred,
            )
        }
    ) { innerPadding ->
        val uiState by weatherViewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = OverviewScreens.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = OverviewScreens.Start.name) {
                HomeScreen(
                    regionState = uiState.regionList,
                    regionApiState = weatherViewModel.regionApiState,
                ) {
                    weatherViewModel.setRegionId(it)
                    navController.navigate(OverviewScreens.CountryOverview.name)
                }
            }

            composable(route = OverviewScreens.CountryOverview.name) {
                CountryScreen()
            }

            composable(route = OverviewScreens.Starred.name) {
                StarredScreen()
            }

            composable(route =  OverviewScreens.AppInfo.name) {
                AppInfoScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SkySnapTheme {
        SkySnapApp()
    }
}