package edu.ucne.tasktally.data.local.entidades

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "racha",
    foreignKeys = [
        ForeignKey(
            entity = GemaEntity::class,
            parentColumns = ["gemaId"],
            childColumns = ["gemaId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("gemaId")]
)
data class RachaEntity(
    @PrimaryKey(autoGenerate = true)
    val rachaId: Int = 0,
    val gemaId: Int,
    val dias: Int
)
