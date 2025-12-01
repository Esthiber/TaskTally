package edu.ucne.tasktally.data.local.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recompensa")
data class RecompensaEntity(
    @PrimaryKey(autoGenerate = true)
    val recompensaId: Int = 0,
    val titulo: String,
    val costo: Double
)