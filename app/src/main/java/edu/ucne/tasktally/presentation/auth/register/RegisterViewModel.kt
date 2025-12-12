package edu.ucne.tasktally.presentation.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.tasktally.data.remote.Resource
import edu.ucne.tasktally.domain.usecases.auth.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onEvent(event: RegisterUiEvent) {
        when (event) {
            is RegisterUiEvent.UsernameChanged -> {
                _uiState.update { it.copy(username = event.username) }
            }

            is RegisterUiEvent.EmailChanged -> {
                _uiState.update { it.copy(email = event.email) }
            }

            is RegisterUiEvent.PasswordChanged -> {
                _uiState.update { it.copy(password = event.password) }
            }

            is RegisterUiEvent.ConfirmPasswordChanged -> {
                _uiState.update { it.copy(confirmPassword = event.confirmPassword) }
            }

            is RegisterUiEvent.RoleChanged -> {
                _uiState.update { it.copy(role = event.role) }
            }

            RegisterUiEvent.RegisterClick -> {
                register()
            }

            RegisterUiEvent.ClearError -> {
                _uiState.update { it.copy(error = null) }
            }

            RegisterUiEvent.ClearSuccess -> {
                _uiState.update {
                    it.copy(
                        registrationSuccess = false,
                        successMessage = null
                    )
                }
            }

            RegisterUiEvent.ResetForm -> {
                _uiState.value = RegisterUiState()
            }
        }
    }

    private fun register() {
        val currentState = _uiState.value
        if (currentState.isLoading) return

        _uiState.update {
            it.copy(
                isLoading = true,
                error = null
            )
        }

        viewModelScope.launch {
            when (val result = registerUseCase(
                currentState.username,
                currentState.password,
                currentState.confirmPassword,
                currentState.email.takeIf { it.isNotBlank() },
                currentState.role
            )) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = null,
                            registrationSuccess = true,
                            successMessage = result.data?.message
                                ?: "Registration successful! Please login with your credentials."
                        )
                    }
                }

                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }

                is Resource.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }
}

