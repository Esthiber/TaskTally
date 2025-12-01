package edu.ucne.tasktally.data.mappers

import edu.ucne.tasktally.data.local.entidades.TareaEntity
import edu.ucne.tasktally.domain.models.Tarea

fun TareaEntity.toDomain(): Tarea {
    return Tarea(
        tareaId = tareaId,
        titulo = titulo,
        descripcion = descripcion,
        estadoId = estadoId,
        puntos = puntos,
        fechaCreacion = fechaCreacion,
        fechaVencimiento = fechaVencimiento,
        recurrente = recurrente
    )
}

fun Tarea.toEntity(): TareaEntity {
    return TareaEntity(
        tareaId = tareaId,
        titulo = titulo,
        descripcion = descripcion,
        estadoId = estadoId,
        puntos = puntos,
        fechaCreacion = fechaCreacion,
        fechaVencimiento = fechaVencimiento,
        recurrente = recurrente
    )
}