package edu.ucne.tasktally.data.local.entidades

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "persona",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId")]
)
data class PersonaEntity(
    @PrimaryKey(autoGenerate = true)
    val personaId: Int = 0,
    val userId: Int = 0,
    val nombre: String,
    val rewardTotal: Double = 0.0
)