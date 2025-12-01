package edu.ucne.tasktally.domain.repository

import edu.ucne.tasktally.domain.models.PersonaRecompensa
import kotlinx.coroutines.flow.Flow

interface PersonaRecompensaRepository {
    fun observePersonaRecompensas(): Flow<List<PersonaRecompensa>>
    suspend fun getPersonaRecompensa(id: Int?): PersonaRecompensa?
    suspend fun upsert(personaRecompensa: PersonaRecompensa): Int
    suspend fun delete(personaRecompensa: PersonaRecompensa)
    suspend fun deleteById(id: Int)
}