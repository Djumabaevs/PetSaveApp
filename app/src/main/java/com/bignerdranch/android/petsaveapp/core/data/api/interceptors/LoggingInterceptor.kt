package com.bignerdranch.android.petsaveapp.core.data.api.interceptors


class LoggingInterceptor @Inject constructor() : HttpLoggingInterceptor.Logger {
  override fun log(message: String) {
    Logger.i(message)
  }
}