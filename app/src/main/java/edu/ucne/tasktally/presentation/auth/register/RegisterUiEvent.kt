package edu.ucne.tasktally.presentation.auth.register

sealed interface RegisterUiEvent {
    data class UsernameChanged(val username: String) : RegisterUiEvent
    data class EmailChanged(val email: String) : RegisterUiEvent
    data class PasswordChanged(val password: String) : RegisterUiEvent
    data class ConfirmPasswordChanged(val confirmPassword: String) : RegisterUiEvent
    data class RoleChanged(val role: String) : RegisterUiEvent
    data object RegisterClick : RegisterUiEvent
    data object ClearError : RegisterUiEvent
    data object ClearSuccess : RegisterUiEvent
    data object ResetForm : RegisterUiEvent
}