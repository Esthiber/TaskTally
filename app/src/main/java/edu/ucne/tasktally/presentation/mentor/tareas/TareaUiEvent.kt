package edu.ucne.tasktally.presentation.mentor.tareas

sealed interface TareaUiEvent {
    data class OnTituloChange(val value: String) : TareaUiEvent
    data class OnDescripcionChange(val value: String) : TareaUiEvent
    data class OnPuntosChange(val value: String) : TareaUiEvent
    data class OnRepetirChange(val value: String) : TareaUiEvent
    data class OnDiaToggle(val dia: String) : TareaUiEvent
    data class OnImageSelected(val imageName: String) : TareaUiEvent
    data object OnShowImagePicker : TareaUiEvent
    data object OnDismissImagePicker : TareaUiEvent
    data object Save : TareaUiEvent
}