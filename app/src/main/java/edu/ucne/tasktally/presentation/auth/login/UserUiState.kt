package edu.ucne.tasktally.presentation.auth.login

data class UserUiState(
    val userId: Int,
    val username: String,
    val email: String,
    val role: String = "",
    val mentorId: Int? = null,
    val gemaId: Int? = null
)