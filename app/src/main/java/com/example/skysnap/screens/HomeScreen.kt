package com.example.skysnap.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.skysnap.components.RegionList
import com.example.skysnap.ui.theme.SkySnapTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    regionOverviewModel: RegionOverviewModel = viewModel(factory = RegionOverviewModel.Factory)
) {
    val regionState by regionOverviewModel.uiState.collectAsState()

    val regionApiState = regionOverviewModel.regionApiState

    Box(modifier = modifier) {
        when (regionApiState) {
            is RegionApiState.Loading -> Text("Loading...")
            is RegionApiState.Error -> Text("Couldn't load the api...")
            is RegionApiState.Success -> RegionList(regionOverviewState = regionState)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    SkySnapTheme {
        HomeScreen()
    }
}