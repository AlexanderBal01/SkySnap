package com.example.skysnap.components.homeScreen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.example.skysnap.ui.screens.home.HomeViewModel
import com.example.skysnap.ui.screens.home.states.HomeState
import com.example.skysnap.ui.screens.home.states.LocationListState
import kotlinx.coroutines.launch

@Composable
fun LocationList(
    homeViewModel: HomeViewModel,
    locationListState: LocationListState,
    locationOverviewState: HomeState,
    navigateToWeather: (String) -> Unit
) {
    // Remember LazyListState to manage scrolling
    val lazyListState = rememberLazyListState()

    // Compose LazyColumn to display a list of locations
    LazyColumn(state = lazyListState) {
        items(locationListState.locationList) { location ->
            // LocationItem composable for each location
            LocationItem(name = location.name, navigateToWeather = navigateToWeather)
        }
    }

    // Coroutine scope for launching scrolling effects
    val coroutineScope = rememberCoroutineScope()

    // LaunchedEffect to scroll to the specified item when the index changes
    LaunchedEffect(locationOverviewState.scrollActionIdx) {
        if (locationOverviewState.scrollActionIdx != 0) {
            coroutineScope.launch {
                // Animate scroll to the specified item index
                lazyListState.animateScrollToItem(locationOverviewState.scrollToItemIndex)
            }
        }
    }
}
