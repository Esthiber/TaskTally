package edu.ucne.tasktally.data.repositories

import edu.ucne.tasktally.data.local.DAOs.TareaDao
import edu.ucne.tasktally.data.mappers.toDomain
import edu.ucne.tasktally.data.mappers.toEntity
import edu.ucne.tasktally.domain.models.Tarea
import edu.ucne.tasktally.domain.repository.TareaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TareaRepositoryImpl @Inject constructor(
    private val dao: TareaDao
) : TareaRepository {

    override fun observeTareas(): Flow<List<Tarea>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    override suspend fun getTarea(id: Int?): Tarea? =
        dao.getById(id)?.toDomain()

    override suspend fun upsert(tarea: Tarea): Int {
        dao.upsert(tarea.toEntity())
        return tarea.tareaId
    }

    override suspend fun delete(tarea: Tarea) {
        dao.delete(tarea.toEntity())
    }

    override suspend fun deleteById(id: Int) {
        dao.deleteById(id)
    }
}