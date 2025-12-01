package edu.ucne.tasktally.data.local.DAOs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.tasktally.data.local.entidades.PersonaRecompensaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonaRecompensaDao {
    @Query("SELECT * FROM personaRecompensa ORDER BY personaRecompensaId DESC")
    fun observeAll(): Flow<List<PersonaRecompensaEntity>>

    @Query("SELECT * FROM personaRecompensa WHERE personaRecompensaId = :id")
    suspend fun getById(id: Int?): PersonaRecompensaEntity?

    @Query("SELECT * FROM personaRecompensa WHERE personaId = :personaId")
    fun observeByPersona(personaId: Int): Flow<List<PersonaRecompensaEntity>>

    @Upsert
    suspend fun upsert(entity: PersonaRecompensaEntity)

    @Delete
    suspend fun delete(entity: PersonaRecompensaEntity)

    @Query("DELETE FROM personaRecompensa WHERE personaRecompensaId = :id")
    suspend fun deleteById(id: Int)
}