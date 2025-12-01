package edu.ucne.tasktally.data.local.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "estado")
class EstadoEntity (
    @PrimaryKey(autoGenerate = true)
    val estadoId: Int = 0,
    val nombre: String
)