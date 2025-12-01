package edu.ucne.tasktally.domain.repository

import edu.ucne.tasktally.domain.models.Recompensa
import kotlinx.coroutines.flow.Flow

interface RecompensaRepository {
    fun observeRecompensas(): Flow<List<Recompensa>>
    suspend fun getRecompensa(id: Int?): Recompensa?
    suspend fun upsert(recompensa: Recompensa): Int
    suspend fun delete(recompensa: Recompensa)
    suspend fun deleteById(id: Int)
}