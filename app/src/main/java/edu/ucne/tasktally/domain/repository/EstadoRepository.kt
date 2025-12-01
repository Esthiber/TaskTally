package edu.ucne.tasktally.domain.repository
import edu.ucne.tasktally.domain.models.Estado
import kotlinx.coroutines.flow.Flow

interface EstadoRepository {
    fun observeEstados(): Flow<List<Estado>>
    suspend fun getEstado(id: Int?): Estado?
    suspend fun upsert(estado: Estado): Int
    suspend fun delete(estado: Estado)
    suspend fun deleteById(id: Int)
}