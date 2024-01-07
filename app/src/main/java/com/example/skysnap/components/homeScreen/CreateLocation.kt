package com.example.skysnap.components.homeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.window.Dialog
import com.example.skysnap.R

@Composable
fun CreateLocation(
    locationName: String,
    onLocationNameChanged: (String) -> Unit,
    onLocationSaved: () -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Card(
            shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius)),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.large_padding)),
            ) {
                OutlinedTextField(
                    value = locationName,
                    onValueChange = onLocationNameChanged,
                    label = { Text(stringResource(R.string.searchbar_location_label)) },
                    textStyle = TextStyle(MaterialTheme.colorScheme.primary)
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height)))
                Row {
                    Spacer(Modifier.weight(1F))
                    TextButton(onClick = onDismissRequest) {
                        Text(stringResource(R.string.cancel))
                    }
                    Spacer(Modifier.width(dimensionResource(id = R.dimen.spacer_height)))
                    TextButton(onClick = onLocationSaved) {
                        Text(stringResource(R.string.add))
                    }
                }
            }
        }
    }
}