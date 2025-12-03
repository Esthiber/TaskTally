package edu.ucne.tasktally.data.remote.DTOs.auth

import com.squareup.moshi.Json

data class LoginResponse(
    @Json(name = "success") val success: Boolean,
    @Json(name = "message") val message: String?,
    @Json(name = "token") val token: String?,
    @Json(name = "refreshToken") val refreshToken: String?,
    @Json(name = "user") val user: UserInfo?,
    @Json(name = "expiresAt") val expiresAt: String?
)

data class UserInfo(
    @Json(name = "userId") val userId: Int,
    @Json(name = "userName") val userName: String,
    @Json(name = "email") val email: String?
)
