package edu.ucne.tasktally.domain.models

data class Persona(
    val personaId: Int = 0,
    val userId: Int = 0,
    val nombre: String,
    val rewardTotal: Double = 0.0
)
