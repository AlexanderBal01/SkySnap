package com.example.skysnap.ui

import androidx.compose.foundation.layout.width
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.skysnap.R
import com.example.skysnap.navigation.enums.OverviewScreens
import com.example.skysnap.navigation.util.NavigationType
import com.example.skysnap.ui.theme.SkySnapTheme

@Composable
fun SkySnapApp(
    modifier: Modifier = Modifier,
    navigationType: NavigationType,
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val goHome: () -> Unit = {
        navController.popBackStack(
            OverviewScreens.Home.name,
            inclusive = false,
        )
    }

    val goToFavourites =
        { navController.navigate(OverviewScreens.Favourites.name) { launchSingleTop = true } }

    val currentScreenTitle = OverviewScreens.valueOf(
        backStackEntry?.destination?.route ?: OverviewScreens.SkySnap.name
    ).title

    var isAddNewLocation by remember {
        mutableStateOf(false)
    }

    if (navigationType == NavigationType.PERMANENT_NAVIGATION_DRAWER) {
        PermanentNavigationDrawer(drawerContent = {
            PermanentDrawerSheet (
                modifier.width(
                    dimensionResource(id = R.dimen.drawer_width)
                )
            ) {

            }
        }) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun skySnapAppPreview() {
    SkySnapTheme {
        SkySnapApp(navigationType = NavigationType.BOTTOM_NAVIGATION)
    }
}