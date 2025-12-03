package edu.ucne.tasktally.data.repositories

import edu.ucne.tasktally.data.local.DAOs.GemaDao
import edu.ucne.tasktally.data.mappers.toDomain
import edu.ucne.tasktally.data.mappers.toEntity
import edu.ucne.tasktally.domain.models.Gema
import edu.ucne.tasktally.domain.repository.GemaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import java.util.UUID

class GemaRepositoryImpl @Inject constructor(
    private val dao: GemaDao
) : GemaRepository {

    override fun observeGemas(): Flow<List<Gema>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    override suspend fun getGema(id: String?): Gema? =
        dao.getById(id)?.toDomain()

    override suspend fun upsert(gema: Gema): String {
        val entityToSave = if (gema.id.isBlank()) {
            gema.toEntity().copy(id = UUID.randomUUID().toString())
        } else {
            gema.toEntity()
        }
        dao.upsert(entityToSave)
        return entityToSave.id
    }

    override suspend fun delete(gema: Gema) {
        dao.delete(gema.toEntity())
    }

    override suspend fun deleteById(id: String) {
        dao.deleteById(id)
    }
}