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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.skysnap.model.Country

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryList(
    modifier: Modifier = Modifier,
    countryOverviewState: List<Country>,
    onCountryItemClicked: (String) -> Unit
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
        label = { Text(text = "Zoek hier voor een stad") },
        modifier = modifier.padding(start = 8.dp, end = 8.dp).fillMaxWidth(),
        shape = RoundedCornerShape(15.dp)
    )
    Spacer(modifier = modifier.height(8.dp))
    LazyColumn(state = lazyListState, modifier = modifier.padding(start = 8.dp, end = 8.dp)) {
        items(filterItems(countryOverviewState, searchText)) {
            CountryItem(name = it.name, id= it.id, onCountryItemClicked = onCountryItemClicked)
            Spacer(modifier = modifier.height(8.dp))
        }
    }
}

private fun filterItems(items: List<Country>, searchText: String): List<Country> {
    return if (searchText.isEmpty()) {
        items
    } else {
        items.filter { item -> item.name.contains(searchText, ignoreCase = true) }
    }
}