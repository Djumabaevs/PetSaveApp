package com.bignerdranch.android.petsaveapp.core.data.api.interceptors

import com.bignerdranch.android.logging.Logger
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject


class LoggingInterceptor @Inject constructor() : HttpLoggingInterceptor.Logger {
  override fun log(message: String) {
    Logger.i(message)
  }
}