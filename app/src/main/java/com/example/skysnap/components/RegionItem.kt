package com.example.skysnap.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

@Composable
fun RegionItem(
    modifier: Modifier = Modifier,
    name: String = "",
    id: String = "",
    onRegionItemClicked: (String) -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize()
            .requiredHeight(72.dp)
            .clip(shape = RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.primary)
            .clickable { onRegionItemClicked(id) }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 16.dp,
                    end = 24.dp,
                    top = 8.dp,
                    bottom = 8.dp,
                ),
        ) {
            Text(
                text = name,
                color = MaterialTheme.colorScheme.onPrimary,
                lineHeight = 1.5.em,
                style = TextStyle(
                fontSize = 16.sp,
                ),
                modifier = Modifier
                    .wrapContentHeight(align = Alignment.CenterVertically)
                    .align(Alignment.CenterVertically),
            )
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "Icons/arrow_right_24px",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.CenterVertically),
            )
        }
    }
}