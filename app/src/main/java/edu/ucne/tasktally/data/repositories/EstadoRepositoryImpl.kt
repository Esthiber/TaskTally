package edu.ucne.tasktally.data.repositories

import edu.ucne.tasktally.data.local.DAOs.EstadoDao
import edu.ucne.tasktally.data.mappers.toDomain
import edu.ucne.tasktally.data.mappers.toEntity
import edu.ucne.tasktally.domain.models.Estado
import edu.ucne.tasktally.domain.repository.EstadoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EstadoRepositoryImpl @Inject constructor(
    private val dao: EstadoDao
) : EstadoRepository {

    override fun observeEstados(): Flow<List<Estado>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    override suspend fun getEstado(id: Int?): Estado? =
        dao.getById(id)?.toDomain()

    override suspend fun upsert(estado: Estado): Int {
        dao.upsert(estado.toEntity())
        return estado.estadoId
    }

    override suspend fun delete(estado: Estado) {
        dao.delete(estado.toEntity())
    }

    override suspend fun deleteById(id: Int) {
        dao.deleteById(id)
    }
}