package com.example.skysnap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.skysnap.ui.SkySnapApp
import com.example.skysnap.ui.theme.SkySnapTheme
import com.example.skysnap.ui.util.NavigationType

/**
 * The main activity of the SkySnap app, extending [ComponentActivity].
 */
class MainActivity : ComponentActivity() {

    /**
     * Called when the activity is first created. Responsible for setting up the UI using Jetpack Compose.
     */
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the content of the activity to the Jetpack Compose UI.
        setContent {
            SkySnapTheme {
                // Load the background image from resources.
                val image = painterResource(R.drawable.backgroundimage)

                // Set up the main content area using Jetpack Compose.
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Determine the window size class to adapt UI based on available screen space.
                    val windowSize = calculateWindowSizeClass(activity = this)

                    // Display the background image, scaling it to fill the entire screen.
                    Image(
                        painter = image,
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize(),
                    )

                    // Choose the appropriate UI layout based on the window size class.
                    when (windowSize.widthSizeClass) {
                        WindowWidthSizeClass.Compact -> {
                            SkySnapApp(navigationType = NavigationType.BOTTOM_NAVIGATION)
                        }
                        WindowWidthSizeClass.Medium -> {
                            SkySnapApp(navigationType = NavigationType.NAVIGATION_RAIL)
                        }
                        WindowWidthSizeClass.Expanded -> {
                            SkySnapApp(navigationType = NavigationType.PERMANENT_NAVIGATION_DRAWER)
                        }
                        else -> {
                            SkySnapApp(navigationType = NavigationType.BOTTOM_NAVIGATION)
                        }
                    }
                }
            }
        }
    }
}
