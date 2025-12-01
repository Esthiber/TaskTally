package edu.ucne.tasktally.presentation.usuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.tasktally.data.remote.Resource
import edu.ucne.tasktally.domain.models.Usuario
import edu.ucne.tasktally.domain.usecases.CreateUsuarioLocalUseCase
import edu.ucne.tasktally.domain.usecases.GetUsuariosRemoteUseCase
import edu.ucne.tasktally.domain.usecases.GetUsuariosUseCase
import edu.ucne.tasktally.domain.usecases.TriggerSyncUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsuarioViewModel @Inject constructor(
    private val getUsuariosUseCase: GetUsuariosUseCase,
    private val getUsuariosRemoteUseCase: GetUsuariosRemoteUseCase,
    private val createUsuarioLocalUseCase: CreateUsuarioLocalUseCase,
    private val triggerSyncUseCase: TriggerSyncUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UsuarioUiState(isLoading = true))
    val state: StateFlow<UsuarioUiState> = _state.asStateFlow()

    init {
        observeUsuarios()
    }

    private fun observeUsuarios() {
        viewModelScope.launch {
            getUsuariosUseCase().collect { usuarios ->
                _state.update {
                    it.copy(
                        usuarios = usuarios,
                        isLoading = false
                    )
                }
            }
        }
    }

    suspend fun onEvent(event: UsuarioUiEvent) {
        when (event) {
            is UsuarioUiEvent.CrearUsuario -> {
                crearUsuario(event.userName, event.password)
            }

            is UsuarioUiEvent.Login -> {
                login(event.userName, event.password)
            }

            is UsuarioUiEvent.OnUserNameChange -> {
                _state.update { it.copy(userName = event.userName) }
            }

            is UsuarioUiEvent.OnPasswordChange -> {
                _state.update { it.copy(password = event.password) }
            }

            is UsuarioUiEvent.OnLoginUserNameChange -> {
                _state.update { it.copy(loginUserName = event.userName) }
            }

            is UsuarioUiEvent.OnLoginPasswordChange -> {
                _state.update { it.copy(loginPassword = event.password) }
            }

            is UsuarioUiEvent.ShowCreateSheet -> {
                _state.update { it.copy(showCreateSheet = true) }
            }

            is UsuarioUiEvent.HideCreateSheet -> {
                _state.update {
                    it.copy(
                        showCreateSheet = false,
                        userName = "",
                        password = ""
                    )
                }
            }

            is UsuarioUiEvent.UserMessageShown -> {
                _state.update { it.copy(userMessage = null) }
            }

            is UsuarioUiEvent.Logout -> {
                _state.update {
                    it.copy(
                        isLoggedIn = false,
                        loggedInUser = null,
                        loginUserName = "",
                        loginPassword = ""
                    )
                }
            }
        }
    }

    private suspend fun crearUsuario(userName: String, password: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        val usuario = Usuario(userName = userName, password = password)
        when (val result = createUsuarioLocalUseCase(usuario)) {
            is Resource.Success -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        userMessage = "Usuario creado localmente. Se sincronizará cuando haya conexión.",
                        showCreateSheet = false,
                        userName = "",
                        password = ""
                    )
                }
                triggerSyncUseCase()
            }

            is Resource.Error -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        userMessage = result.message ?: "Error al crear usuario"
                    )
                }
            }

            else -> {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private suspend fun login(userName: String, password: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        try {
            // Obtener usuarios de la API para validar login
            val usuariosRemote = getUsuariosRemoteUseCase()

            // Buscar usuario que coincida con las credenciales
            val usuarioEncontrado = usuariosRemote.find { usuario ->
                usuario.userName == userName && usuario.password == password
            }

            if (usuarioEncontrado != null) {
                // Login exitoso
                val usuario = Usuario(
                    id = usuarioEncontrado.usuarioId?.toString() ?: "",
                    remoteId = usuarioEncontrado.usuarioId,
                    userName = usuarioEncontrado.userName,
                    password = usuarioEncontrado.password
                )

                _state.update {
                    it.copy(
                        isLoading = false,
                        isLoggedIn = true,
                        loggedInUser = usuario,
                        userMessage = "Bienvenido ${usuario.userName}",
                        loginUserName = "",
                        loginPassword = ""
                    )
                }
            } else {
                // Credenciales incorrectas
                _state.update {
                    it.copy(
                        isLoading = false,
                        userMessage = "Usuario o contraseña incorrectos"
                    )
                }
            }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    isLoading = false,
                    userMessage = "Error de conexión. Verifique su internet e intente nuevamente."
                )
            }
        }
    }
}

