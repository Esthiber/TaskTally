package edu.ucne.tasktally.data.local.entidades

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "usuarios",
    indices = [
        Index(value = ["userName"], unique = true),
        Index(value = ["remoteId"]),
        Index(value = ["isPendingCreate"])
    ]
)
data class UsuarioEntity (
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val remoteId: Int? = null,
    val userName: String,
    val password: String,
    val isPendingCreate: Boolean = false,
)