package edu.ucne.tasktally.data.mappers

import edu.ucne.tasktally.data.local.entidades.UserInfoEntity
import edu.ucne.tasktally.domain.models.UserInfo

fun UserInfoEntity.toDomain() = UserInfo(
    userInfoId = userInfoId,
    nombre = nombre,
    apellido = apellido,
    fechaNacimiento = fechaNacimiento
)

fun UserInfo.toEntity() = UserInfoEntity(
    userInfoId = userInfoId,
    nombre = nombre,
    apellido = apellido,
    fechaNacimiento = fechaNacimiento
)