package edu.ucne.tasktally.data.local.DAOs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.tasktally.data.local.entidades.PersonaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonaDao {
    @Query("SELECT * FROM persona ORDER BY personaId DESC")
    fun observeAll(): Flow<List<PersonaEntity>>

    @Query("SELECT * FROM persona WHERE personaId = :id")
    suspend fun getById(id: Int?): PersonaEntity?

    @Upsert
    suspend fun upsert(persona: PersonaEntity)

    @Delete
    suspend fun delete(persona: PersonaEntity)

    @Query("DELETE FROM persona WHERE personaId = :id")
    suspend fun deleteById(id: Int)
}