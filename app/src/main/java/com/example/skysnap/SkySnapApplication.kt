package com.example.skysnap

import android.app.Application
import com.example.skysnap.data.AppContainer
import com.example.skysnap.data.DefaultAppContainer

class SkySnapApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}