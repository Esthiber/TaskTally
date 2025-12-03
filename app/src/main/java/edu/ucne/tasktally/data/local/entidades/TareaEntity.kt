package edu.ucne.tasktally.data.local.entidades

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tareas",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["userId"],
            childColumns = ["createdBy"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ZonaEntity::class,
            parentColumns = ["zonaId"],
            childColumns = ["zonaId"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = EstadoEntity::class,
            parentColumns = ["estadoId"],
            childColumns = ["estadoId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index("estadoId"),Index("zonaId"),Index("createdBy")]
)
data class TareaEntity(
    @PrimaryKey(autoGenerate = true)
    val tareaId: Int = 0,
    val createdBy: Int,
    val zonaId: Int,
    val estadoId: Int?,
    val titulo: String,
    val descripcion: String,
    val puntos: Double,
    val diaAsignada: String?,
    val recurrente: String?,
    val imgVector: String?,
    val fechaCreacion: String
)
