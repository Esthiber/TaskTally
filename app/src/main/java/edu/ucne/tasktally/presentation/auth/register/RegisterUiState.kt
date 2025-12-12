package edu.ucne.tasktally.presentation.auth.register

data class RegisterUiState(
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val email: String = "",
    val role: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val registrationSuccess: Boolean = false,
    val successMessage: String? = null
)