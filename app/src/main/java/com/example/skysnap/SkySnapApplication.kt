package com.example.skysnap

import android.app.Application
import com.example.skysnap.data.AppContainer
import com.example.skysnap.data.DefaultAppContainer

/**
 * Custom application class for the SkySnap app, extending the Android [Application] class.
 */
class SkySnapApplication: Application() {

    /**
     * Property to hold the application-wide dependency container.
     */
    lateinit var container: AppContainer

    /**
     * Called when the application is starting. This is where most initialization should go,
     * including setting up the dependency injection container.
     */
    override fun onCreate() {
        super.onCreate()

        // Initialize the AppContainer, which manages dependencies for the application.
        container = DefaultAppContainer(context = applicationContext)
    }
}