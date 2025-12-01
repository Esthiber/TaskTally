package edu.ucne.tasktally.domain.repository

import edu.ucne.tasktally.domain.models.Persona
import kotlinx.coroutines.flow.Flow

interface PersonaRepository {
    fun observePersonas(): Flow<List<Persona>>
    suspend fun getPersona(id: Int?): Persona?
    suspend fun upsert(persona: Persona): Int
    suspend fun delete(persona: Persona)
    suspend fun deleteById(id: Int)
}