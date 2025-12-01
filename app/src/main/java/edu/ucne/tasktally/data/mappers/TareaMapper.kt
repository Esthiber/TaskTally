package edu.ucne.tasktally.data.mappers

import edu.ucne.tasktally.data.local.entidades.TareaEntity
import edu.ucne.tasktally.domain.models.Tarea

fun TareaEntity.toDomain(): Tarea {
    return Tarea(
        tareaId = tareaId,
        createdBy = createdBy,
        zonaId = zonaId,
        estadoId = estadoId,
        titulo = titulo,
        descripcion = descripcion,
        puntos = puntos,
        diaAsignada = diaAsignada,
        recurrente = recurrente,
        imgVector = imgVector,
        fechaCreacion = fechaCreacion
    )
}

fun Tarea.toEntity(): TareaEntity {
    return TareaEntity(
        tareaId = tareaId,
        createdBy = createdBy,
        zonaId = zonaId,
        estadoId = estadoId,
        titulo = titulo,
        descripcion = descripcion,
        puntos = puntos,
        diaAsignada = diaAsignada,
        recurrente = recurrente,
        imgVector = imgVector,
        fechaCreacion = fechaCreacion
    )
}