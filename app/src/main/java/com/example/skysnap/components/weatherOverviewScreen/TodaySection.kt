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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.skysnap.R
import com.example.skysnap.ui.screens.weatherOverview.states.WeatherOverviewUiState

@Composable
fun TodaySection(
    modifier: Modifier = Modifier,
    uiStateWeather: WeatherOverviewUiState
) {

    Card(
        modifier = modifier
            .padding(dimensionResource(R.dimen.card_outer_padding))
    ) {
        Column (
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
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
                text = uiStateWeather.location?.name.toString(),
                color = Color.White,
                fontSize = 45.sp,
                textAlign = TextAlign.Center,
                modifier = modifier.fillMaxWidth()
            )
            Text(
                text = "${uiStateWeather.weather?.temp}°C",
                color = Color.White,
                fontSize = 45.sp,
                textAlign = TextAlign.Center,
                modifier = modifier.fillMaxWidth()
            )
        }
    }
}