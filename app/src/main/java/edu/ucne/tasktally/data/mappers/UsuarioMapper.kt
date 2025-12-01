package edu.ucne.tasktally.data.mappers

import edu.ucne.tasktally.data.local.entidades.UsuarioEntity
import edu.ucne.tasktally.domain.models.Usuario

fun UsuarioEntity.toDomain(): Usuario {
    return Usuario(
        id = id,
        remoteId = remoteId,
        userName = userName,
        password = password,
        isPendingCreate = isPendingCreate
    )
}

fun Usuario.toEntity(): UsuarioEntity {
    return UsuarioEntity(
        id = id,
        remoteId = remoteId,
        userName = userName,
        password = password,
        isPendingCreate = isPendingCreate
    )
}
