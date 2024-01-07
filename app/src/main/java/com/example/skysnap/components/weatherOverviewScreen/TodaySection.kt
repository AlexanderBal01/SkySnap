package com.example.skysnap.components.weatherOverviewScreen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.skysnap.R
import com.example.skysnap.ui.theme.SkySnapTheme

@Composable
fun TodaySection(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(dimensionResource(R.dimen.card_outer_padding))
    ) {
        Column (
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onPrimary)
                .padding(dimensionResource(R.dimen.card_padding))
        ) {
            Text(
                text = "Dendermonde",
                color = Color.White,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = modifier.fillMaxWidth()
            )
            AsyncImage(
                model = "https://openweathermap.org/img/w/10d.png",
                contentDescription = null,
            )
            Text(
                text = "20°C",
                color = Color.White,
                fontSize = 45.sp,
                textAlign = TextAlign.Center,
                modifier = modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodaySectionPreview() {
    SkySnapTheme {
        TodaySection()
    }
}