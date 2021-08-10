package com.bignerdranch.android.petsaveapp

import android.app.Application
import com.bignerdranch.android.logging.Logger
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class PetSaveApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        RemoteConfigUtil.init(BuildConfig.DEBUG)

        initLogger()
    }

    private fun initLogger() {
        com.bignerdranch.android.logging.Logger.init()
    }
}