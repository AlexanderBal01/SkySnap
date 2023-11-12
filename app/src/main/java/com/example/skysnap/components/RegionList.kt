package com.example.skysnap.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.skysnap.screens.RegionState

@Composable
fun RegionList(modifier: Modifier = Modifier, regionOverviewState: RegionState) {
    val lazyListState = rememberLazyListState()

    LazyColumn(state = lazyListState) {
        items(regionOverviewState.currentRegionList) {
            RegionItem(name = it.name, id = it.id)
        }
    }
}