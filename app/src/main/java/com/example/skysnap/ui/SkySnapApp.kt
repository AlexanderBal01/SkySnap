package com.example.skysnap.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.skysnap.navigation.util.NavigationType
import com.example.skysnap.ui.theme.SkySnapTheme

@Composable
fun skySnapApp(
    modifier: Modifier = Modifier,
    navigationType: NavigationType,
    navController: NavHostController = rememberNavController(),
) {
}

@Preview(showBackground = true)
@Composable
fun skySnapAppPreview() {
    SkySnapTheme {
        skySnapApp(navigationType = NavigationType.BOTTOM_NAVIGATION)
    }
}