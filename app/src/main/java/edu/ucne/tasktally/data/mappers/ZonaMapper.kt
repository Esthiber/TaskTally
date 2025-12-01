package edu.ucne.tasktally.data.mappers

import edu.ucne.tasktally.data.local.entidades.ZonaEntity
import edu.ucne.tasktally.domain.models.Zona

fun ZonaEntity.toDomain() = Zona(
    zonaId = zonaId,
    mentorId = mentorId,
    zonaName = zonaName
)

fun Zona.toEntity() = ZonaEntity(
    zonaId = zonaId,
    mentorId = mentorId,
    zonaName = zonaName
)