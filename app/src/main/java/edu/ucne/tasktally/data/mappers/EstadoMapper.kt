package edu.ucne.tasktally.data.mappers

import edu.ucne.tasktally.data.local.entidades.EstadoEntity
import edu.ucne.tasktally.domain.models.Estado

fun EstadoEntity.toDomain(): Estado {
    return Estado(
        estadoId = estadoId,
        nombre = nombre
    )
}

fun Estado.toEntity(): EstadoEntity {
    return EstadoEntity(
        estadoId = estadoId,
        nombre = nombre
    )
}