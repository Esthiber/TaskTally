package edu.ucne.tasktally.presentation.usuario

sealed interface UsuarioUiEvent {
    // Eventos de creación de usuario
    data class CrearUsuario(val userName: String, val password: String) : UsuarioUiEvent

    // Eventos de login
    data class Login(val userName: String, val password: String) : UsuarioUiEvent

    // Eventos de cambio de campos de registro
    data class OnUserNameChange(val userName: String) : UsuarioUiEvent
    data class OnPasswordChange(val password: String) : UsuarioUiEvent

    // Eventos de cambio de campos de login
    data class OnLoginUserNameChange(val userName: String) : UsuarioUiEvent
    data class OnLoginPasswordChange(val password: String) : UsuarioUiEvent

    // Eventos de bottom sheet
    object ShowCreateSheet : UsuarioUiEvent
    object HideCreateSheet : UsuarioUiEvent

    // Eventos de mensajes
    object UserMessageShown : UsuarioUiEvent

    // Evento de logout
    object Logout : UsuarioUiEvent
}