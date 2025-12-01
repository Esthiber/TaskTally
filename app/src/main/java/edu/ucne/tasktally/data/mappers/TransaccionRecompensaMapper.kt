package edu.ucne.tasktally.data.mappers

import edu.ucne.tasktally.data.local.entidades.TransaccionRecompensaEntity
import edu.ucne.tasktally.domain.models.TransaccionRecompensa

fun TransaccionRecompensaEntity.toDomain() = TransaccionRecompensa(
    transaccionRecompensaId = transaccionRecompensaId,
    gemaId = gemaId,
    recompensaId = recompensaId,
    costoPuntos = costoPuntos,
    fechaTransaccion = fechaTransaccion
)

fun TransaccionRecompensa.toEntity() = TransaccionRecompensaEntity(
    transaccionRecompensaId = transaccionRecompensaId,
    gemaId = gemaId,
    recompensaId = recompensaId,
    costoPuntos = costoPuntos,
    fechaTransaccion = fechaTransaccion
)