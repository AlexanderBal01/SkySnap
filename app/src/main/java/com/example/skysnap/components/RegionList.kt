package com.example.skysnap.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.skysnap.data.WeatherUiState
import com.example.skysnap.model.Region

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegionList(
    modifier: Modifier = Modifier,
    regionOverviewState: State<WeatherUiState>,
    onRegionItemClicked: (String) -> Unit
) {
    val lazyListState = rememberLazyListState()
    var searchText by rememberSaveable {
        mutableStateOf("")
    }

    Spacer(modifier = modifier.height(8.dp))
    OutlinedTextField(
        value = searchText,
        onValueChange = {
            searchText = it
        },
        label = { Text(text = "Zoek hier voor een regio") },
        modifier = modifier.padding(start = 8.dp, end = 8.dp).fillMaxWidth(),
        shape = RoundedCornerShape(15.dp)
    )
    Spacer(modifier = modifier.height(8.dp))
    LazyColumn(state = lazyListState, modifier = modifier.padding(start = 8.dp, end = 8.dp)) {
        items(filterItems(regionOverviewState.value.regionList, searchText)) {
            RegionItem(name = it.name, id = it.id, onRegionItemClicked = onRegionItemClicked)
            Spacer(modifier = modifier.height(8.dp))
        }
    }
}

private fun filterItems(items: List<Region>, searchText: String): List<Region> {
    return if (searchText.isEmpty()) {
        items
    } else {
        items.filter { item ->
            item.name.contains(searchText, ignoreCase = true)
        }
    }
}