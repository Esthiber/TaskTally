package edu.ucne.tasktally.presentation.zona

sealed interface ZonaUiEvent {
    object LoadZona : ZonaUiEvent

    data class OnZonaNameChange(val name: String) : ZonaUiEvent
    object StartEditingName : ZonaUiEvent
    object SaveZonaName : ZonaUiEvent
    object CancelEditingName : ZonaUiEvent
    object GenerateNewJoinCode : ZonaUiEvent
    object ShowJoinCodeDialog : ZonaUiEvent
    object HideJoinCodeDialog : ZonaUiEvent

    object UserMessageShown : ZonaUiEvent
    object Refresh : ZonaUiEvent
}
