package edu.ucne.tasktally.domain.models

import java.util.UUID

data class Usuario(
    val id: String = UUID.randomUUID().toString(),
    val remoteId: Int? = null,
    val userName: String,
    val password: String,
    val isPendingCreate: Boolean = false
)
