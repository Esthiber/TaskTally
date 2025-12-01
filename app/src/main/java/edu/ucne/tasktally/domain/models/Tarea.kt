package edu.ucne.tasktally.domain.models

data class Tarea(
    val tareaId: Int = 0,
    val titulo: String,
    val descripcion: String = "",
    val estadoId: Int? = null,
    val puntos: Double,
    val fechaCreacion: String,
    val fechaVencimiento: String,
    val recurrente: String
)
