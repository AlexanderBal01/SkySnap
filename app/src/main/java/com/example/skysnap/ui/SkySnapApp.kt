package com.example.skysnap.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.skysnap.R
import com.example.skysnap.components.navigation.AppBar
import com.example.skysnap.components.navigation.NavigationDrawerContent
import com.example.skysnap.components.navigation.NavigationRail
import com.example.skysnap.ui.screens.home.HomeViewModel
import com.example.skysnap.ui.screens.navigation.NavComponent
import com.example.skysnap.ui.screens.weatherOverview.WeatherOverviewViewModel
import com.example.skysnap.ui.theme.SkySnapTheme
import com.example.skysnap.ui.util.NavigationType

/**
 * Composable function representing the main structure of the SkySnap application.
 *
 * @param modifier Modifier for styling the layout.
 * @param navigationType Type of navigation UI pattern to be used in the application.
 * @param navController Navigation controller for handling navigation within the app.
 * @param homeViewModel ViewModel for the Home screen providing data and logic.
 * @param weatherOverviewViewModel ViewModel for the Weather Overview screen providing data and logic.
 */
@Composable
fun SkySnapApp(
    modifier: Modifier = Modifier,
    navigationType: NavigationType,
    navController: NavHostController = rememberNavController(),
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
    weatherOverviewViewModel: WeatherOverviewViewModel = viewModel(factory = WeatherOverviewViewModel.Factory)
) {
    // State to track the visibility of the "Add" button.
    var isAddNewVisible by remember { mutableStateOf(false) }

    // Callback for navigating up in the navigation hierarchy.
    val navigateUp: () -> Unit = { navController.navigateUp() }

    // Check if there is a previous destination in the navigation stack.
    val canNavigateBack = navController.previousBackStackEntry != null

    // Based on the specified navigation type, different UI structures are used.
    when (navigationType) {
        NavigationType.PERMANENT_NAVIGATION_DRAWER -> {
            // Permanent navigation drawer layout.
            PermanentNavigationDrawer(
                drawerContent = {
                    PermanentDrawerSheet (modifier.width(dimensionResource(R.dimen.drawer_width))) {
                        NavigationDrawerContent(
                            selectedDestination = navController.currentDestination,
                            onTabPressed = { node: String -> navController.navigate(node) },
                            modifier = modifier
                                .wrapContentWidth()
                                .fillMaxHeight()
                                .background(MaterialTheme.colorScheme.primary)
                                .padding(dimensionResource(R.dimen.drawer_padding_content))
                        )
                    }
                }
            ) {
                // Scaffold with AppBar and FloatingActionButton for the Permanent Navigation Drawer.
                Scaffold(
                    containerColor = Color.Transparent,
                    topBar = {
                        AppBar(
                            canNavigateBack = canNavigateBack,
                            navigateUp = navigateUp
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { isAddNewVisible = !isAddNewVisible },
                            containerColor = MaterialTheme.colorScheme.primary
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = stringResource(id = R.string.add)
                            )
                        }
                    }
                ) { innerPadding ->
                    // Navigation component for handling different screens.
                    NavComponent(
                        navController = navController,
                        modifier = modifier.padding(innerPadding),
                        fabActionVisible = isAddNewVisible,
                        fabResetAction = { isAddNewVisible = false },
                        homeViewModel = homeViewModel,
                        weatherOverviewViewModel = weatherOverviewViewModel
                    )
                }
            }
        }
        NavigationType.BOTTOM_NAVIGATION -> {
            // Scaffold layout with Bottom Navigation.
            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    AppBar(
                        canNavigateBack = canNavigateBack,
                        navigateUp = navigateUp
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { isAddNewVisible = !isAddNewVisible },
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(id = R.string.add),
                            tint = Color.White
                        )
                    }
                }
            ) { innerPadding ->
                // Navigation component for handling different screens.
                NavComponent(
                    navController = navController,
                    modifier = modifier.padding(innerPadding),
                    fabActionVisible = isAddNewVisible,
                    fabResetAction = { isAddNewVisible = false },
                    homeViewModel = homeViewModel,
                    weatherOverviewViewModel = weatherOverviewViewModel
                )
            }
        }
        else -> {
            // Default layout with Navigation Rail or Row.
            Row {
                // Display Navigation Rail if specified.
                AnimatedVisibility(visible = navigationType == NavigationType.NAVIGATION_RAIL) {
                    NavigationRail(
                        selectedDestination = navController.currentDestination,
                        onTabPressed = { node: String -> navController.navigate(node) }
                    )
                }
                // Scaffold layout with AppBar and FloatingActionButton.
                Scaffold (
                    containerColor = Color.Transparent,
                    topBar = {
                        AppBar(
                            canNavigateBack = canNavigateBack,
                            navigateUp = navigateUp
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { isAddNewVisible = !isAddNewVisible },
                            containerColor = MaterialTheme.colorScheme.primary
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = stringResource(id = R.string.add)
                            )
                        }
                    }
                ) { innerPadding ->
                    // Navigation component for handling different screens.
                    NavComponent(
                        navController = navController,
                        modifier = modifier.padding(innerPadding),
                        fabActionVisible = isAddNewVisible,
                        fabResetAction = { isAddNewVisible = false },
                        homeViewModel = homeViewModel,
                        weatherOverviewViewModel = weatherOverviewViewModel
                    )
                }
            }
        }
    }
}

/**
 * Preview function for the SkySnapApp.
 */
@Preview(showBackground = true)
@Composable
fun SkySnapAppPreview() {
    SkySnapTheme {
        SkySnapApp(navigationType = NavigationType.BOTTOM_NAVIGATION)
    }
}
