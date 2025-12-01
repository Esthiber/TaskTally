package edu.ucne.tasktally.data.mappers

import edu.ucne.tasktally.data.local.entidades.GemaZonaEntity
import edu.ucne.tasktally.domain.models.GemaZona

fun GemaZonaEntity.toDomain() = GemaZona(
    gemaZonaId = gemaZonaId,
    gemaId = gemaId,
    zonaId = zonaId,
    fechaIngreso = fechaIngreso
)

fun GemaZona.toEntity() = GemaZonaEntity(
    gemaZonaId = gemaZonaId,
    gemaId = gemaId,
    zonaId = zonaId,
    fechaIngreso = fechaIngreso
)