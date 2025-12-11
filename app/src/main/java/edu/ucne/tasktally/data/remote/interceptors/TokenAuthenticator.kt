package edu.ucne.tasktally.data.remote.interceptors

import edu.ucne.tasktally.data.local.preferences.AuthPreferencesManager
import edu.ucne.tasktally.data.remote.AuthApi
import edu.ucne.tasktally.data.remote.DTOs.auth.RefreshTokenRequest
import edu.ucne.tasktally.data.remote.DTOs.auth.RefreshTokenResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(
    private val authPreferencesManager: AuthPreferencesManager,
    private val authApi: AuthApi
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code != 401) return null

        return runBlocking {
            try {
                val newAccessToken = getNewAccessToken() ?: run {
                    authPreferencesManager.clearAuthData()
                    return@runBlocking null
                }

                response.request.newBuilder()
                    .header("Authorization", "Bearer $newAccessToken")
                    .build()

            } catch (e: Exception) {
                authPreferencesManager.clearAuthData()
                return@runBlocking null
            }
        }
    }

    private suspend fun getNewAccessToken(): String? {
        val refreshToken = authPreferencesManager.refreshToken.first()
        if (refreshToken.isNullOrEmpty()) return null

        val refreshResponse = authApi.refreshToken(RefreshTokenRequest(refreshToken))

        if (!refreshResponse.isSuccessful || refreshResponse.body() == null) return null

        val refreshData: RefreshTokenResponse = refreshResponse.body()!!

        if (!refreshData.success || refreshData.token == null) return null

        authPreferencesManager.updateTokens(
            accessToken = refreshData.token,
            refreshToken = refreshData.refreshToken ?: refreshToken,
            expiresAt = refreshData.expiresAt
        )

        return refreshData.token
    }
}