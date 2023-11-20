package com.example.skysnap.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.skysnap.components.RegionList

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    regionState: List<com.example.skysnap.model.Region>,
    regionApiState: RegionApiState,
    onRegionItemClicked: (String) -> Unit,
) {
    Column(modifier = modifier.padding(start = 8.dp, end = 8.dp)) {
        when (regionApiState) {
            is RegionApiState.Loading -> Text("Loading...")
            is RegionApiState.Error -> Text("Couldn't load the api...")
            is RegionApiState.Success -> RegionList(regionOverviewState = regionState, onRegionItemClicked = onRegionItemClicked)
        }
    }
}