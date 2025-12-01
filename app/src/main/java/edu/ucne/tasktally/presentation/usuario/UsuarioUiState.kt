package edu.ucne.tasktally.presentation.usuario

import edu.ucne.tasktally.domain.models.Usuario

data class UsuarioUiState(
    val isLoading: Boolean = false,
    val userMessage: String? = null,
    val showCreateSheet: Boolean = false,

    // Lista de usuarios obtenidos de la API
    val usuarios: List<Usuario> = emptyList(),

    // Campos del formulario de registro
    val userName: String = "",
    val password: String = "",

    // Campos del formulario de login
    val loginUserName: String = "",
    val loginPassword: String = "",

    // Estado de autenticación
    val isLoggedIn: Boolean = false,
    val loggedInUser: Usuario? = null
)
