package edu.ucne.tasktally.data.repositories

import edu.ucne.tasktally.data.local.DAOs.RecompensaDao
import edu.ucne.tasktally.data.mappers.toDomain
import edu.ucne.tasktally.data.mappers.toEntity
import edu.ucne.tasktally.domain.models.Recompensa
import edu.ucne.tasktally.domain.repository.RecompensaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecompensaRepositoryImpl @Inject constructor(
    private val dao: RecompensaDao
) : RecompensaRepository {

    override fun observeRecompensas(): Flow<List<Recompensa>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    override suspend fun getRecompensa(id: Int?): Recompensa? =
        dao.getById(id)?.toDomain()

    override suspend fun upsert(recompensa: Recompensa): Int {
        dao.upsert(recompensa.toEntity())
        return recompensa.recompensaId
    }

    override suspend fun delete(recompensa: Recompensa) {
        dao.delete(recompensa.toEntity())
    }

    override suspend fun deleteById(id: Int) {
        dao.deleteById(id)
    }
}