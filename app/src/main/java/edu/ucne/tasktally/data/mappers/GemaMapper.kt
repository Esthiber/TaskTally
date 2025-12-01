package edu.ucne.tasktally.data.mappers

import edu.ucne.tasktally.data.local.entidades.GemaEntity
import edu.ucne.tasktally.domain.models.Gema

fun GemaEntity.toDomain() = Gema(
    gemaId = gemaId,
    userId = userId,
    userInfoId = userInfoId,
    puntosActuales = puntosActuales,
    puntosTotales = puntosTotales
)

fun Gema.toEntity() = GemaEntity(
    gemaId = gemaId,
    userId = userId,
    userInfoId = userInfoId,
    puntosActuales = puntosActuales,
    puntosTotales = puntosTotales
)