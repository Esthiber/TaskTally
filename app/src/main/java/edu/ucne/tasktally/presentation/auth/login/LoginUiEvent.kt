package edu.ucne.tasktally.presentation.auth.login

sealed interface LoginUiEvent {
    data class UsernameChanged(val username: String) : LoginUiEvent
    data class PasswordChanged(val password: String) : LoginUiEvent
    data object LoginClick : LoginUiEvent
    data object LogoutClick : LoginUiEvent
    data object ClearError : LoginUiEvent
    data object RefreshZoneAccess : LoginUiEvent
}