package com.bignerdranch.android.petsaveapp

import android.app.Application
import com.bignerdranch.android.logging.Logger
import com.djumabaevs.remoteconfig.RemoteConfigUtil
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class PetSaveApplication: Application() {
    // initiate analytics, crashlytics, etc
    override fun onCreate() {
        super.onCreate()

        initLogger()

        RemoteConfigUtil.init(BuildConfig.DEBUG)
    }

    private fun initLogger() {
        Logger.init()
    }
}