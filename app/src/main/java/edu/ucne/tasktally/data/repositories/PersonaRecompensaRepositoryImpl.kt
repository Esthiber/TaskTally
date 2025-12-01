package edu.ucne.tasktally.data.repositories

import edu.ucne.tasktally.data.local.DAOs.PersonaRecompensaDao
import edu.ucne.tasktally.data.mappers.toDomain
import edu.ucne.tasktally.data.mappers.toEntity
import edu.ucne.tasktally.domain.models.PersonaRecompensa
import edu.ucne.tasktally.domain.repository.PersonaRecompensaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PersonaRecompensaRepositoryImpl @Inject constructor(
    private val dao: PersonaRecompensaDao
) : PersonaRecompensaRepository {

    override fun observePersonaRecompensas(): Flow<List<PersonaRecompensa>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    override suspend fun getPersonaRecompensa(id: Int?): PersonaRecompensa? =
        dao.getById(id)?.toDomain()

    override suspend fun upsert(personaRecompensa: PersonaRecompensa): Int {
        dao.upsert(personaRecompensa.toEntity())
        return personaRecompensa.personaRecompensaId
    }

    override suspend fun delete(personaRecompensa: PersonaRecompensa) {
        dao.delete(personaRecompensa.toEntity())
    }

    override suspend fun deleteById(id: Int) {
        dao.deleteById(id)
    }
}