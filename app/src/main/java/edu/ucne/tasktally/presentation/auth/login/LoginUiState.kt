package edu.ucne.tasktally.presentation.auth.login

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentUser: UserUiState? = null,
    val hasZoneAccess: Boolean = false
)