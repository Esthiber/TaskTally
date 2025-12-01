package edu.ucne.tasktally.data.repositories

import edu.ucne.tasktally.data.local.DAOs.PersonaDao
import edu.ucne.tasktally.data.mappers.toDomain
import edu.ucne.tasktally.data.mappers.toEntity
import edu.ucne.tasktally.domain.models.Persona
import edu.ucne.tasktally.domain.repository.PersonaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class PersonaRepositoryImpl @Inject constructor(
    private val dao: PersonaDao
) : PersonaRepository {

    override fun observePersonas(): Flow<List<Persona>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    override suspend fun getPersona(id: Int?): Persona? =
        dao.getById(id)?.toDomain()

    override suspend fun upsert(persona: Persona): Int {
        dao.upsert(persona.toEntity())
        return persona.personaId
    }

    override suspend fun delete(persona: Persona) {
        dao.delete(persona.toEntity())
    }

    override suspend fun deleteById(id: Int) {
        dao.deleteById(id)
    }
}