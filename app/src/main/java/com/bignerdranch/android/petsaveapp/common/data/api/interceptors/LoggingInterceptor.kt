package com.bignerdranch.android.petsaveapp.common.data.api.interceptors

import com.bignerdranch.android.petsaveapp.Logger
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

class LoggingInterceptor @Inject constructor() : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        Logger.i(message)
    }
}