package edu.ucne.tasktally.data.local.entidades

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "recompensa",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["userId"],
            childColumns = ["createdBy"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices=[Index("createdBy")]
)
data class RecompensaEntity(
    @PrimaryKey(autoGenerate = true)
    val recompensaId: Int = 0,
    val createdBy: Int,
    val titulo: String,
    val descripcion: String,
    val precio: Double,
    val isDisponible: Boolean,
    val fechaCreacion: String
)