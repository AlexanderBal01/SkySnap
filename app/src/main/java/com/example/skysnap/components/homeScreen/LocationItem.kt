package com.example.skysnap.components.homeScreen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.skysnap.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationItem(
    modifier: Modifier = Modifier,
    name: String = "",
    navigateToWeather: (String) -> Unit,
) {
    Card(
        modifier = modifier.padding(dimensionResource(R.dimen.card_outer_padding)),
        onClick = {
            navigateToWeather(name)
        }
    ) {
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
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
        ){
            Text(text = name, color = Color.White, fontSize = 20.sp)
            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = stringResource(
                R.string.navigate_to_weather_overview
            ), tint = Color.White, modifier = modifier.size(dimensionResource(R.dimen.card_icon)))
        }
    }
}