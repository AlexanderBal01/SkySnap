package com.example.skysnap.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.skysnap.screens.RegionState

@Composable
fun RegionList(modifier: Modifier = Modifier, regionOverviewState: RegionState, navController: NavController) {
    val lazyListState = rememberLazyListState()

    Spacer(modifier = modifier.height(8.dp))
    LazyColumn(state = lazyListState, modifier = modifier.padding(start = 8.dp, end = 8.dp)) {
        items(regionOverviewState.currentRegionList) {
            RegionItem(name = it.name, id = it.id, navController = navController)
            Spacer(modifier = modifier.height(8.dp))
        }
    }
}