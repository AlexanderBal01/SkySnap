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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.skysnap.R
import com.example.skysnap.components.navigation.AppBar
import com.example.skysnap.components.navigation.NavigationDrawerContent
import com.example.skysnap.components.navigation.NavigationRail
import com.example.skysnap.ui.screens.home.HomeViewModel
import com.example.skysnap.ui.screens.navigation.NavComponent
import com.example.skysnap.ui.screens.navigation.OverviewScreens
import com.example.skysnap.ui.theme.SkySnapTheme
import com.example.skysnap.ui.util.NavigationType

@Composable
fun SkySnapApp(
    modifier: Modifier = Modifier,
    navigationType: NavigationType,
    navController: NavHostController = rememberNavController(),
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
) {
    var isAddNewVisible by remember { mutableStateOf(false) }

    val backStackEntry by navController.currentBackStackEntryAsState()

    val navigateUp: () -> Unit = { navController.navigateUp() }

    val currentScreenTitle = OverviewScreens.valueOf(
        backStackEntry?.destination?.route ?: OverviewScreens.Home.name,
    ).title

    val isHomeScreen = currentScreenTitle == OverviewScreens.Home.title

    val canNavigateBack = navController.previousBackStackEntry != null

    when (navigationType) {
        NavigationType.PERMANENT_NAVIGATION_DRAWER -> {
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
                Scaffold(
                    containerColor = Color.Transparent,
                    topBar = {
                        AppBar(
                            title = currentScreenTitle,
                            canNavigateBack = canNavigateBack,
                            navigateUp = navigateUp
                        )
                    },
                    floatingActionButton = {
                        if (isHomeScreen) {
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
                    }
                ) { innerPadding ->
                    NavComponent(
                        navController = navController,
                        modifier = modifier.padding(innerPadding),
                        fabActionVisible = isAddNewVisible,
                        fabResetAction = { isAddNewVisible = false },
                        homeViewModel = homeViewModel,
                    )
                }
            }
        }
        NavigationType.BOTTOM_NAVIGATION -> {
            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    AppBar(
                        title = currentScreenTitle,
                        canNavigateBack = canNavigateBack,
                        navigateUp = navigateUp
                    )
                },
                floatingActionButton = {
                    if (isHomeScreen) {
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
                }
            ) { innerPadding ->
                NavComponent(
                    navController = navController,
                    modifier = modifier.padding(innerPadding),
                    fabActionVisible = isAddNewVisible,
                    fabResetAction = { isAddNewVisible = false },
                    homeViewModel = homeViewModel,
                )
            }
        }
        else -> {
            Row {
                AnimatedVisibility(visible = navigationType == NavigationType.NAVIGATION_RAIL) {
                    stringResource(R.string.navigation_rail)
                    NavigationRail(
                        selectedDestination = navController.currentDestination,
                        onTabPressed = { node: String -> navController.navigate(node) }
                    )
                }
                Scaffold (
                    containerColor = Color.Transparent,
                    topBar = {
                        AppBar(
                            title = currentScreenTitle,
                            canNavigateBack = canNavigateBack,
                            navigateUp = navigateUp
                        )
                    },
                    floatingActionButton = {
                        if (isHomeScreen) {
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
                    }
                ) { innerPadding ->
                    NavComponent(
                        navController = navController,
                        modifier = modifier.padding(innerPadding),
                        fabActionVisible = isAddNewVisible,
                        fabResetAction = { isAddNewVisible = false },
                        homeViewModel = homeViewModel,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SkySnapAppPreview() {
    SkySnapTheme {
        SkySnapApp(navigationType = NavigationType.BOTTOM_NAVIGATION)
    }
}