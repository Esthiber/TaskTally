package edu.ucne.tasktally.data.mappers

import edu.ucne.tasktally.data.local.entidades.RecompensaEntity
import edu.ucne.tasktally.domain.models.Recompensa

fun RecompensaEntity.toDomain(): Recompensa {
    return Recompensa(
        recompensaId = recompensaId,
        createdBy = createdBy,
        titulo = titulo,
        descripcion = descripcion,
        precio = precio,
        isDisponible = isDisponible,
        fechaCreacion = fechaCreacion
    )
}

fun Recompensa.toEntity(): RecompensaEntity {
    return RecompensaEntity(
        recompensaId = recompensaId,
        createdBy = createdBy,
        titulo = titulo,
        descripcion = descripcion,
        precio = precio,
        isDisponible = isDisponible,
        fechaCreacion = fechaCreacion
    )
}