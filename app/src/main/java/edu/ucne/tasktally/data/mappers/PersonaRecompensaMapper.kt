package edu.ucne.tasktally.data.mappers

import edu.ucne.tasktally.data.local.entidades.PersonaRecompensaEntity
import edu.ucne.tasktally.domain.models.PersonaRecompensa

fun PersonaRecompensaEntity.toDomain(): PersonaRecompensa {
    return PersonaRecompensa(
        personaRecompensaId = personaRecompensaId,
        personaId = personaId,
        recompensaId = recompensaId,
        fechaCanje = fechaCanje
    )
}

fun PersonaRecompensa.toEntity(): PersonaRecompensaEntity {
    return PersonaRecompensaEntity(
        personaRecompensaId = personaRecompensaId,
        personaId = personaId,
        recompensaId = recompensaId,
        fechaCanje = fechaCanje
    )
}