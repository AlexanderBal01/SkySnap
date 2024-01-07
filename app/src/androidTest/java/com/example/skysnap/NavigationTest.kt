package com.example.skysnap

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.skysnap.ui.SkySnapApp
import com.example.skysnap.ui.util.NavigationType
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {
    private val someLocationName: String = "Dendermonde"

    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var navController: TestNavHostController
    
    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent { 
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            SkySnapApp(navController = navController, navigationType = NavigationType.BOTTOM_NAVIGATION)
        }
    }

    @Test
    fun verifyStartDestination() {
        composeTestRule
            .onNodeWithText("Home")
            .assertIsDisplayed()
    }

    @Test
    fun clickAddLocation() {
        composeTestRule
            .onNodeWithContentDescription("Add location")
            .performClick()

        composeTestRule
            .onNodeWithText("Search here for a location")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Add location")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Cancel")
            .assertIsDisplayed()
    }

    @Test
    fun canAddLocation() {
        composeTestRule
            .onNodeWithContentDescription("Add location")
            .performClick()

        composeTestRule
            .onNodeWithText("Add location")
            .performClick()

        composeTestRule
            .onNodeWithText("Home")
            .assertIsDisplayed()
    }

    @Test
    fun canCancelAddLocation() {
        composeTestRule
            .onNodeWithContentDescription("Add location")
            .performClick()

        composeTestRule
            .onNodeWithText("Cancel")
            .performClick()

        composeTestRule
            .onNodeWithText("Home")
            .assertIsDisplayed()
    }
}