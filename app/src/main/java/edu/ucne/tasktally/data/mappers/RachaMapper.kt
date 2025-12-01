package edu.ucne.tasktally.data.mappers

import edu.ucne.tasktally.data.local.entidades.RachaEntity
import edu.ucne.tasktally.domain.models.Racha

fun RachaEntity.toDomain() = Racha(
    rachaId = rachaId,
    gemaId = gemaId,
    dias = dias
)

fun Racha.toEntity() = RachaEntity(
    rachaId = rachaId,
    gemaId = gemaId,
    dias = dias
)