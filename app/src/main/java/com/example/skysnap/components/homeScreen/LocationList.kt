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
    val lazyListState = rememberLazyListState()
    LazyColumn(state = lazyListState) {
        items(locationListState.locationList) {
            LocationItem(name = it.name, navigateToWeather = navigateToWeather)
        }
    }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(locationOverviewState.scrollActionIdx) {
        if (locationOverviewState.scrollActionIdx != 0) {
            coroutineScope.launch {
                lazyListState.animateScrollToItem(locationOverviewState.scrollToItemIndex)
            }
        }
    }
}