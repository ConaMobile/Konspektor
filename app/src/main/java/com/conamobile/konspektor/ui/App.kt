package com.conamobile.konspektor.ui

import android.app.Application
import com.conamobile.konspektor.core.utils.UtilObjects
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        UtilObjects.isFirstLogin = true
    }
}