package edu.ucne.tasktally.data.local.entidades

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tareas",
    foreignKeys = [
        ForeignKey(
            entity = EstadoEntity::class,
            parentColumns = ["estadoId"],
            childColumns = ["estadoId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("estadoId")]
)
data class TareaEntity(
    @PrimaryKey(autoGenerate = true)
    val tareaId: Int = 0,
    val titulo: String,
    val descripcion: String = "",
    val estadoId: Int? = null,
    val puntos: Double,
    val fechaCreacion: String,
    val fechaVencimiento: String,
    val recurrente: String
)
