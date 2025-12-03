package edu.ucne.tasktally.data.remote.DTOs.auth

data class LoginRequest(
    val userName: String,
    val password: String
)
