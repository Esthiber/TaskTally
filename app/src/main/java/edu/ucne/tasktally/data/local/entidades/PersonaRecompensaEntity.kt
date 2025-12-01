package edu.ucne.tasktally.data.local.entidades

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "personaRecompensa",
    foreignKeys = [
        ForeignKey(
            entity = PersonaEntity::class,
            parentColumns = ["personaId"],
            childColumns = ["personaId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = RecompensaEntity::class,
            parentColumns = ["recompensaId"],
            childColumns = ["recompensaId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("personaId"), Index("recompensaId")]
)
data class PersonaRecompensaEntity(
    @PrimaryKey(autoGenerate = true)
    val personaRecompensaId: Int = 0,
    val personaId: Int = 0,
    val recompensaId: Int = 0,
    val fechaCanje: String
)