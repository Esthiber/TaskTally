package edu.ucne.tasktally.presentation.mentor.recompensas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.tasktally.data.local.preferences.AuthPreferencesManager
import edu.ucne.tasktally.domain.models.RecompensaMentor
import edu.ucne.tasktally.domain.usecases.GetRecompensaMentorUseCase
import edu.ucne.tasktally.domain.usecases.UpsertRecompensaMentorUseCase
import edu.ucne.tasktally.domain.usecases.mentor.recompensa.CreateRecompensaMentorLocalUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecompensaViewModel @Inject constructor(
    private val createRecompensaMentorLocalUseCase: CreateRecompensaMentorLocalUseCase,
    private val getRecompensaMentorUseCase: GetRecompensaMentorUseCase,
    private val upsertRecompensaMentorUseCase: UpsertRecompensaMentorUseCase,
    private val authPreferencesManager: AuthPreferencesManager
) : ViewModel() {

    private val _state = MutableStateFlow(RecompensaUiState())
    val state: StateFlow<RecompensaUiState> = _state.asStateFlow()

    init {
        loadMentorName()
    }

    private fun loadMentorName() {
        viewModelScope.launch {
            val username = authPreferencesManager.username.first() ?: "Mentor"
            _state.update { it.copy(mentorName = username) }
        }
    }

    fun onEvent(event: RecompensaUiEvent) {
        when (event) {
            is RecompensaUiEvent.OnTituloChange ->
                _state.update { it.copy(titulo = event.value, tituloError = null) }

            is RecompensaUiEvent.OnDescripcionChange ->
                _state.update { it.copy(descripcion = event.value) }

            is RecompensaUiEvent.OnPrecioChange ->
                _state.update { it.copy(precio = event.value, precioError = null) }

            RecompensaUiEvent.OnShowImagePicker ->
                _state.update { it.copy(showImagePicker = true) }

            RecompensaUiEvent.OnDismissImagePicker ->
                _state.update { it.copy(showImagePicker = false) }

            is RecompensaUiEvent.OnImageSelected ->
                _state.update { it.copy(imgVector = event.imageName, showImagePicker = false) }

            RecompensaUiEvent.Save -> onSave()
        }
    }

    fun loadRecompensaParaEdicion(id: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, isEditing = true) }

            try {
                val recompensa = getRecompensaMentorUseCase(id)

                if (recompensa != null) {
                    _state.update {
                        it.copy(
                            recompensaId = recompensa.recompensaId,
                            titulo = recompensa.titulo,
                            descripcion = recompensa.descripcion,
                            precio = recompensa.precio.toString(),
                            imgVector = recompensa.nombreImgVector,
                            isLoading = false
                        )
                    }
                } else {
                    _state.update {
                        it.copy(error = "No se encontró la recompensa", isLoading = false)
                    }
                }

            } catch (e: Exception) {
                _state.update {
                    it.copy(error = e.message ?: "Error al cargar la recompensa", isLoading = false)
                }
            }
        }
    }

    private fun onSave() {
        if (!validarCampos()) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val mentorId = authPreferencesManager.mentorId.first()

            if (mentorId == null) {
                _state.update { it.copy(isLoading = false, error = "No se encontró ID mentor") }
                return@launch
            }

            val st = _state.value

            if (st.isEditing && st.recompensaId != null) {
                updateRecompensa(mentorId, st)
            } else {
                createRecompensa(mentorId, st)
            }
        }
    }

    private suspend fun createRecompensa(mentorId: Int, st: RecompensaUiState) {
        try {
            val recompensa = RecompensaMentor(
                titulo = st.titulo.trim(),
                descripcion = st.descripcion.trim(),
                precio = st.precio.toInt(),
                nombreImgVector = st.imgVector,
                createdBy = mentorId,
                isPendingCreate = true
            )

            createRecompensaMentorLocalUseCase(recompensa)

            _state.update {
                it.copy(
                    isLoading = false,
                    message = "Guardado localmente",
                    navigateBack = true
                )
            }

        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }

    private suspend fun updateRecompensa(mentorId: Int, st: RecompensaUiState) {
        try {
            val recompensa = RecompensaMentor(
                recompensaId = st.recompensaId!!,
                titulo = st.titulo.trim(),
                descripcion = st.descripcion.trim(),
                precio = st.precio.toInt(),
                nombreImgVector = st.imgVector,
                createdBy = mentorId,
                isPendingUpdate = true
            )

            upsertRecompensaMentorUseCase(recompensa)

            _state.update {
                it.copy(
                    isLoading = false,
                    message = "Recompensa actualizada",
                    navigateBack = true
                )
            }

        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message) }
        }
    }

    private fun validarCampos(): Boolean {
        var ok = true
        if (_state.value.titulo.isBlank()) {
            _state.update { it.copy(tituloError = "El título es requerido") }
            ok = false
        }
        if (_state.value.precio.toIntOrNull() == null || _state.value.precio.toInt() <= 0) {
            _state.update { it.copy(precioError = "Precio inválido") }
            ok = false
        }
        return ok
    }

    fun onNavigationHandled() {
        _state.update { it.copy(navigateBack = false) }
    }

    fun onMessageShown() {
        _state.update { it.copy(message = null, error = null) }
    }
}