package com.example.skysnap

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
import com.example.skysnap.screens.appInfo.AppInfoScreen
import com.example.skysnap.screens.CityScreen
import com.example.skysnap.screens.country.CountryScreen
import com.example.skysnap.screens.OverviewScreens
import com.example.skysnap.screens.StarredScreen
import com.example.skysnap.screens.country.CountryViewModel
import com.example.skysnap.screens.region.RegionViewModel
import com.example.skysnap.screens.region.RegionScreen
import com.example.skysnap.ui.theme.SkySnapTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkySnapApp(
    regionViewModel: RegionViewModel = viewModel(factory = RegionViewModel.Factory),
    countryViewModel: CountryViewModel = viewModel(factory = CountryViewModel.Factory)
) {
    val navController = rememberNavController()
    val backStackEntryNav by navController.currentBackStackEntryAsState()

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

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            SkySnapAppBar(
                canNavigateBack = canNavigateBack,
                navigateUp = navigateUp,
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
        NavHost(
            navController = navController,
            startDestination = OverviewScreens.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = OverviewScreens.Start.name) {
                RegionScreen(
                    onRegionItemClicked = {
                        navController.navigate("${OverviewScreens.CountryOverview.name}/${it}")
                    }
                )
            }

            composable(route = "${OverviewScreens.CountryOverview.name}/{regionid}") {backStackEntry ->
                CountryScreen(
                    regionId = backStackEntry.arguments?.getString("regionid", "").toString(),
                    viewModel = countryViewModel,
                    onCountryItemClicked = {}
                )
            }

            composable(route = OverviewScreens.Starred.name) {
                StarredScreen()
            }

            composable(route =  OverviewScreens.AppInfo.name) {
                AppInfoScreen()
            }

            composable(route = OverviewScreens.CityOverview.name) {
                CityScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SkySnapAppPreview() {
    SkySnapTheme {
        SkySnapApp()
    }
}