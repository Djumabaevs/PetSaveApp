package com.bignerdranch.android.petsaveapp.core.data.api.interceptors

import com.bignerdranch.android.petsaveapp.core.data.api.ApiConstants
import com.bignerdranch.android.petsaveapp.core.data.api.ApiConstants.AUTH_ENDPOINT
import com.bignerdranch.android.petsaveapp.core.data.api.ApiParameters.AUTH_HEADER
import com.bignerdranch.android.petsaveapp.core.data.api.ApiParameters.CLIENT_ID
import com.bignerdranch.android.petsaveapp.core.data.api.ApiParameters.CLIENT_SECRET
import com.bignerdranch.android.petsaveapp.core.data.api.ApiParameters.GRANT_TYPE_KEY
import com.bignerdranch.android.petsaveapp.core.data.api.ApiParameters.GRANT_TYPE_VALUE
import com.bignerdranch.android.petsaveapp.core.data.api.ApiParameters.TOKEN_TYPE
import com.bignerdranch.android.petsaveapp.core.data.api.model.ApiToken
import com.bignerdranch.android.petsaveapp.core.data.preferences.Preferences
import okhttp3.Interceptor
import javax.inject.Inject
import com.squareup.moshi.Moshi
import okhttp3.*
import org.threeten.bp.Instant



class AuthenticationInterceptor @Inject constructor(
    private val preferences: Preferences
): Interceptor {

  companion object {
    const val UNAUTHORIZED = 401
  }

  override fun intercept(chain: Interceptor.Chain): Response {
    val token = preferences.getToken()
    val tokenExpirationTime = Instant.ofEpochSecond(preferences.getTokenExpirationTime())
    val request = chain.request()

    // if (chain.request().headers[NO_AUTH_HEADER] != null) return chain.proceed(request)
    val interceptedRequest: Request

    if (tokenExpirationTime.isAfter(Instant.now())) {
      // token is still valid, so we can proceed with the request
      interceptedRequest = chain.createAuthenticatedRequest(token)
    } else {
      // Token expired. Gotta refresh it before proceeding with the actual request
      val tokenRefreshResponse = chain.refreshToken()

      interceptedRequest = if (tokenRefreshResponse.isSuccessful) {
        val newToken = mapToken(tokenRefreshResponse)

        if (newToken.isValid()) {
          storeNewToken(newToken)
          chain.createAuthenticatedRequest(newToken.accessToken!!)
        } else {
          request
        }
      } else {
        request
      }
    }

    return chain.proceedDeletingTokenIfUnauthorized(interceptedRequest)
  }

  private fun Interceptor.Chain.createAuthenticatedRequest(token: String): Request {
    return request()
        .newBuilder()
        .addHeader(AUTH_HEADER, TOKEN_TYPE + token)
        .build()
  }

  private fun Interceptor.Chain.refreshToken(): Response {
    val url = request()
        .url
        .newBuilder(AUTH_ENDPOINT)!!
        .build()

    val body = FormBody.Builder()
        .add(GRANT_TYPE_KEY, GRANT_TYPE_VALUE)
        .add(CLIENT_ID, ApiConstants.KEY)
        .add(CLIENT_SECRET, ApiConstants.SECRET)
        .build()

    val tokenRefresh = request()
        .newBuilder()
        .post(body)
        .url(url)
        .build()

    return proceedDeletingTokenIfUnauthorized(tokenRefresh)
  }

  private fun Interceptor.Chain.proceedDeletingTokenIfUnauthorized(request: Request): Response {
    val response = proceed(request)

    if (response.code == UNAUTHORIZED) {
      preferences.deleteTokenInfo()
    }

    return response
  }

  private fun mapToken(tokenRefreshResponse: Response): ApiToken {
    val moshi = Moshi.Builder().build()
    val tokenAdapter = moshi.adapter<ApiToken>(ApiToken::class.java)
    val responseBody = tokenRefreshResponse.body!! // if successful, this should be good :]

    return tokenAdapter.fromJson(responseBody.string()) ?: ApiToken.INVALID
  }

  private fun storeNewToken(apiToken: ApiToken) {
    with(preferences) {
      putTokenType(apiToken.tokenType!!)
      putTokenExpirationTime(apiToken.expiresAt)
      putToken(apiToken.accessToken!!)
    }
  }
}
