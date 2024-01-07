package com.example.skysnap

import com.example.skysnap.fake.FakeApiLocationRepository
import com.example.skysnap.ui.screens.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class HomeViewModelTest {
    private val someLocation = "Dendermonde"

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun settingNameChangesState() {
        val viewModel = HomeViewModel(
            locationRepository = FakeApiLocationRepository(),
        )
        viewModel.setNewLocationName(someLocation)
        Assert.assertEquals(viewModel.uiState.value.newLocationName, someLocation)
    }
}

class TestDispatcherRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}