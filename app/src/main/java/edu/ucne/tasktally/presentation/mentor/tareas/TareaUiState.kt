package edu.ucne.tasktally.presentation.mentor.tareas

data class TareaUiState(
    val isLoading: Boolean = false,
    val message: String? = null,
    val error: String? = null,

    val tareaId: String? = null,
    val isEditing: Boolean = false,

    val titulo: String = "",
    val descripcion: String = "",
    val puntos: String = "",
    val repetir: String = "1",
    val dias: List<String> = emptyList(),
    val imgVector: String? = null,

    val showImagePicker: Boolean = false,

    val tituloError: String? = null,
    val puntosError: String? = null,
    val repetirError: String? = null,
    val diasError: String? = null,

    val mentorName: String = "Mentor",

    val navigateBack: Boolean = false
)