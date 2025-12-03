package edu.ucne.tasktally.data.local.entidades

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey val userId: Int,
    val userName: String,
    val password: String,
    val email: String
)