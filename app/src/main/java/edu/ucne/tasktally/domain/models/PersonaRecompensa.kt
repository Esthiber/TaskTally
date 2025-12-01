package edu.ucne.tasktally.domain.models

data class PersonaRecompensa(
    val personaRecompensaId: Int = 0,
    val personaId: Int = 0,
    val recompensaId: Int = 0,
    val fechaCanje: String
)
