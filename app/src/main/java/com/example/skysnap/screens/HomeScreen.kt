package com.example.skysnap.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.skysnap.components.RegionList
import com.example.skysnap.ui.theme.SkySnapTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    regionOverviewModel: RegionOverviewModel = viewModel(factory = RegionOverviewModel.Factory),
    navController: NavController
) {
    val regionState by regionOverviewModel.uiState.collectAsState()

    val regionApiState = regionOverviewModel.regionApiState

    Column(modifier = modifier.padding(start = 8.dp, end = 8.dp)) {
        when (regionApiState) {
            is RegionApiState.Loading -> Text("Loading...")
            is RegionApiState.Error -> Text("Couldn't load the api...")
            is RegionApiState.Success -> RegionList(regionOverviewState = regionState, navController = navController)
        }
    }
}