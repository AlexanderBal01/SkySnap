package com.example.skysnap.components.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavDestination
import com.example.skysnap.R
import com.example.skysnap.ui.screens.navigation.OverviewScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawerContent(
    selectedDestination: NavDestination?,
    onTabPressed: ((String) -> Unit),
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // Loop through each navItem in OverviewScreens
        for (navItem in OverviewScreens.values()) {
            NavigationDrawerItem(
                // Check if the current navItem is selected
                selected = selectedDestination?.route == navItem.name,
                label = {
                    Text(
                        text = navItem.name,
                        modifier = modifier.padding(horizontal = dimensionResource(R.dimen.drawer_padding_header))
                    )
                },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.name,
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = Color.Transparent,
                ),
                onClick = { onTabPressed(navItem.name) }
            )
        }
    }
}
