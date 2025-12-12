package edu.ucne.tasktally.presentation.zona

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.tasktally.domain.usecases.auth.GetCurrentUserUseCase
import edu.ucne.tasktally.domain.usecases.gema.zona.GetGemaZonaByIdUseCase
import edu.ucne.tasktally.domain.usecases.mentor.zona.GetMentorZonaByIdUseCase
import edu.ucne.tasktally.domain.usecases.mentor.zona.UpdateZoneCodeRemoteUseCase
import edu.ucne.tasktally.domain.usecases.zona.UpdateZoneNameLocalUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ZonaViewModel @Inject constructor(
    private val getMentorZonaByIdUseCase: GetMentorZonaByIdUseCase,
    private val getGemaZonaByIdUseCase: GetGemaZonaByIdUseCase,
    private val updateZoneNameUseCase: UpdateZoneNameLocalUseCase,
    private val updateZoneCodeUseCase: UpdateZoneCodeRemoteUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ZonaUiState())
    val state: StateFlow<ZonaUiState> = _state.asStateFlow()

    fun onEvent(event: ZonaUiEvent) {
        when (event) {
            is ZonaUiEvent.LoadZona -> loadZona()
            is ZonaUiEvent.OnZonaNameChange -> updateZonaName(event.name)
            is ZonaUiEvent.StartEditingName -> startEditingName()
            is ZonaUiEvent.SaveZonaName -> saveZonaName()
            is ZonaUiEvent.CancelEditingName -> cancelEditingName()
            is ZonaUiEvent.GenerateNewJoinCode -> generateNewJoinCode()
            is ZonaUiEvent.ShowJoinCodeDialog -> showJoinCodeDialog()
            is ZonaUiEvent.HideJoinCodeDialog -> hideJoinCodeDialog()
            is ZonaUiEvent.UserMessageShown -> userMessageShown()
            is ZonaUiEvent.Refresh -> refreshData()
        }
    }

    private fun updateZonaName(name: String) {
        _state.update { it.copy(zonaName = name) }
    }

    private fun startEditingName() {
        _state.update {
            it.copy(
                isEditingName = true,
                zonaName = it.zona?.nombre ?: ""
            )
        }
    }

    private fun cancelEditingName() {
        _state.update {
            it.copy(
                isEditingName = false,
                zonaName = ""
            )
        }
    }

    private fun showJoinCodeDialog() {
        _state.update { it.copy(showJoinCodeDialog = true) }
    }

    private fun hideJoinCodeDialog() {
        _state.update { it.copy(showJoinCodeDialog = false) }
    }

    private fun userMessageShown() {
        _state.update { it.copy(userMessage = null) }
    }

    private fun refreshData() {
        val currentState = _state.value
        if (currentState.currentUserId != null) {
            loadZona()
        }
    }

    private fun loadZona() = viewModelScope.launch {
        val currentUser = getCurrentUserUseCase().first()
        _state.update {
            it.copy(
                isLoading = true,
                currentUserId = currentUser.userId.toString(),
                isMentor = currentUser.role == "mentor"
            )
        }

        try {
            if (_state.value.isMentor) {
                val currentUser = getCurrentUserUseCase().first()
                val mentorId = currentUser.mentorId
                if (mentorId == null) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            userMessage = "ID de mentor inválido"
                        )
                    }
                    return@launch
                }
                val userData = getCurrentUserUseCase().first()
                val zoneId = userData.zoneId

                if (zoneId == null || zoneId <= 0) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            userMessage = "No tienes una zona asignada"
                        )
                    }
                    return@launch
                }

                val zona = getMentorZonaByIdUseCase(mentorId, zoneId)
                _state.update {
                    it.copy(
                        zona = zona,
                        gemas = zona?.gemas ?: emptyList(),
                        isLoading = false
                    )
                }
            } else {
                val user = getCurrentUserUseCase().first()
                val gemaId = user.gemaId
                if (gemaId == null) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            userMessage = "ID de gema inválido"
                        )
                    }
                    return@launch
                }

                val userData = getCurrentUserUseCase().first()
                val zoneId = userData.zoneId

                if (zoneId == null || zoneId <= 0) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            userMessage = "No tienes una zona asignada"
                        )
                    }
                    return@launch
                }

                val zona = getGemaZonaByIdUseCase(gemaId, zoneId)
                _state.update {
                    it.copy(
                        zona = zona,
                        gemas = zona?.gemas ?: emptyList(),
                        isLoading = false
                    )
                }
            }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    isLoading = false,
                    userMessage = "Error al cargar la zona: ${e.message}"
                )
            }
        }
    }

    private fun saveZonaName() = viewModelScope.launch {
        val currentState = _state.value
        val zona = currentState.zona
        val newName = currentState.zonaName.trim()
        val zoneId = zona?.zonaId

        if (zona == null || newName.isEmpty() || zoneId == null) {
            _state.update {
                it.copy(
                    userMessage = "Nombre de zona no válido"
                )
            }
            return@launch
        }

        _state.update { it.copy(isLoading = true) }

        try {
            updateZoneNameUseCase.invoke(zoneId, newName)
            val updatedZona = zona.copy(nombre = newName)
            _state.update {
                it.copy(
                    zona = updatedZona,
                    isEditingName = false,
                    zonaName = "",
                    isLoading = false,
                    userMessage = "Nombre de zona actualizado"
                )
            }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    isLoading = false,
                    userMessage = "Error al actualizar el nombre: ${e.message}"
                )
            }
        }
    }

    private fun generateNewJoinCode() = viewModelScope.launch {
        val currentState = _state.value
        val zona = currentState.zona
        val mentorId = currentState.currentUserId?.toIntOrNull()

        if (zona == null || mentorId == null || zona.zonaId == 0) {
            _state.update {
                it.copy(userMessage = "No hay zona disponible")
            }
            return@launch
        }

        _state.update { it.copy(isLoading = true) }

        try {
            updateZoneCodeUseCase(zona.zonaId, mentorId)
            loadZona()
            _state.update {
                it.copy(
                    isLoading = false,
                    userMessage = "Código de acceso actualizado"
                )
            }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    isLoading = false,
                    userMessage = "Error al generar nuevo código: ${e.message}"
                )
            }
        }
    }
}