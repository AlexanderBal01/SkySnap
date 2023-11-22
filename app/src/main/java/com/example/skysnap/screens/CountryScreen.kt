package com.example.skysnap.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.skysnap.components.CountryList
import com.example.skysnap.components.RegionList
import com.example.skysnap.model.Country

@Composable
fun CountryScreen(
    modifier: Modifier = Modifier,
    countryState: List<Country>,
    countryApiState: CountryApiState,
    onCountryItemClicked: (String) -> Unit,
) {
    Column(modifier = modifier.padding(start = 8.dp, end = 8.dp)) {
        when (countryApiState) {
            is CountryApiState.Loading -> Text("Loading...")
            is CountryApiState.Error -> Text("Couldn't load the api...")
            is CountryApiState.Succes -> CountryList(countryOverviewState = countryState, onCountryItemClicked = onCountryItemClicked)
        }
    }
}