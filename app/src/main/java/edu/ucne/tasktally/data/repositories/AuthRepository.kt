package edu.ucne.tasktally.data.repositories

import edu.ucne.tasktally.data.local.preferences.AuthPreferencesManager
import edu.ucne.tasktally.data.remote.AuthApi
import edu.ucne.tasktally.data.remote.DTOs.auth.*
import edu.ucne.tasktally.data.remote.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authApi: AuthApi,
    private val authPreferencesManager: AuthPreferencesManager
) {
    suspend fun login(username: String, password: String): Resource<LoginResponse> {
        return try {
            val response = authApi.login(LoginRequest(username, password))
            if (response.isSuccessful && response.body() != null) {
                val loginResponse = response.body()!!
                if (loginResponse.success && loginResponse.token != null && loginResponse.user != null) {
                    authPreferencesManager.saveAuthData(
                        accessToken = loginResponse.token,
                        refreshToken = loginResponse.refreshToken ?: "",
                        userId = loginResponse.user.userId,
                        username = loginResponse.user.userName,
                        email = loginResponse.user.email,
                        expiresAt = loginResponse.expiresAt
                    )
                    Resource.Success(loginResponse)
                } else {
                    Resource.Error(loginResponse.message ?: "Login failed")
                }
            } else {
                Resource.Error("Network error: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.message}")
        }
    }

    suspend fun register(username: String, password: String, email: String?): Resource<RegisterResponse> {
        return try {
            val response = authApi.register(RegisterRequest(username, password, email))
            if (response.isSuccessful && response.body() != null) {
                val registerResponse = response.body()!!
                if (registerResponse.success) {
                    Resource.Success(registerResponse)
                } else {
                    Resource.Error(registerResponse.message ?: "Registration failed")
                }
            } else {
                Resource.Error("Network error: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.message}")
        }
    }

    suspend fun refreshToken(): Resource<RefreshTokenResponse> {
        return try {
            val refreshToken = authPreferencesManager.refreshToken.first()
            if (refreshToken.isNullOrEmpty()) {
                return Resource.Error("No refresh token available")
            }

            val response = authApi.refreshToken(RefreshTokenRequest(refreshToken))
            if (response.isSuccessful && response.body() != null) {
                val refreshResponse = response.body()!!
                if (refreshResponse.success && refreshResponse.token != null) {
                    authPreferencesManager.updateTokens(
                        accessToken = refreshResponse.token,
                        refreshToken = refreshResponse.refreshToken ?: refreshToken,
                        expiresAt = refreshResponse.expiresAt
                    )
                    Resource.Success(refreshResponse)
                } else {
                    Resource.Error("Token refresh failed")
                }
            } else {
                Resource.Error("Network error: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.message}")
        }
    }

    suspend fun logout(): Resource<Unit> {
        return try {
            val refreshToken = authPreferencesManager.refreshToken.first()
            if (!refreshToken.isNullOrEmpty()) {
                authApi.logout(LogoutRequest(refreshToken))
            }

            authPreferencesManager.clearAuthData()
            Resource.Success(Unit)
        } catch (e: Exception) {
            authPreferencesManager.clearAuthData()
            Resource.Success(Unit)
        }
    }

    fun isLoggedIn(): Flow<Boolean> = authPreferencesManager.isLoggedIn

    fun getAccessToken(): Flow<String?> = authPreferencesManager.accessToken

    fun getCurrentUser(): Flow<Triple<Int?, String?, String?>> {
        return kotlinx.coroutines.flow.combine(
            authPreferencesManager.userId,
            authPreferencesManager.username,
            authPreferencesManager.email
        ) { userId, username, email ->
            Triple(userId, username, email)
        }
    }
}
