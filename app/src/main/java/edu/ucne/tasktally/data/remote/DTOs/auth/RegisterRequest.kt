package edu.ucne.tasktally.data.remote.DTOs.auth

import com.squareup.moshi.Json

data class RegisterRequest(
    @Json(name = "userName") val userName: String,
    @Json(name = "password") val password: String,
    @Json(name = "email") val email: String?
)

data class RegisterResponse(
    @Json(name = "success") val success: Boolean,
    @Json(name = "message") val message: String?,
    @Json(name = "userId") val userId: Int?
)
