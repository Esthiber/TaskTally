package edu.ucne.tasktally.data.mappers

import edu.ucne.tasktally.data.local.entidades.PersonaEntity
import edu.ucne.tasktally.domain.models.Persona

fun PersonaEntity.toDomain(): Persona {
    return Persona(
        personaId = personaId,
        userId = userId,
        nombre = nombre,
        rewardTotal = rewardTotal
    )
}

fun Persona.toEntity(): PersonaEntity {
    return PersonaEntity(
        personaId = personaId,
        userId = userId,
        nombre = nombre,
        rewardTotal = rewardTotal
    )
}