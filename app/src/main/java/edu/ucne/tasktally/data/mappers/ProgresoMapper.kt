package edu.ucne.tasktally.data.mappers

import edu.ucne.tasktally.data.local.entidades.ProgresoEntity
import edu.ucne.tasktally.domain.models.Progreso

fun ProgresoEntity.toDomain() = Progreso(
    progresoId = progresoId,
    gemaId = gemaId,
    tareaId = tareaId,
    estadoId = estadoId,
    fechaAsignacion = fechaAsignacion,
    fechaCompletada = fechaCompletada,
    puntosGanados = puntosGanados
)

fun Progreso.toEntity() = ProgresoEntity(
    progresoId = progresoId,
    gemaId = gemaId,
    tareaId = tareaId,
    estadoId = estadoId,
    fechaAsignacion = fechaAsignacion,
    fechaCompletada = fechaCompletada,
    puntosGanados = puntosGanados
)