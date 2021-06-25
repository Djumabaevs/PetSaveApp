package com.bignerdranch.android.petsaveapp.common.data.api.interceptors

import android.os.Build
import com.bignerdranch.android.petsaveapp.common.data.api.ApiConstants
import com.bignerdranch.android.petsaveapp.common.data.preferences.Preferences
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest= Config.NONE, sdk = [Build.VERSION_CODES.P])
class AuthenticationInterceptorTest {

    private lateinit var preferences: Preferences
    private lateinit var mockWebServer: MockWebServer
    private lateinit var authenticationInterceptor: AuthenticationInterceptor
    private lateinit var okHttpClient: OkHttpClient

    private val endpointSeparator = "/"
    private val animalsEndpointPath = endpointSeparator + ApiConstants.ANIMALS_ENDPOINT
    private val authEndpointPath = endpointSeparator + ApiConstants.AUTH_ENDPOINT
    private val validToken = "validToken"
    private val expiredToken = "expiredToken"

}