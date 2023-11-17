package com.example.skysnap.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.skysnap.R

@Composable
fun RegionItem(
    modifier: Modifier = Modifier,
    name: String = "",
    id: String = "",
) {
    Button(onClick = { /*TODO*/ }) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessMedium,
                        ),
                    )
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
            ){
                Text(text = name)
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = stringResource(R.string.go_to_region),
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}