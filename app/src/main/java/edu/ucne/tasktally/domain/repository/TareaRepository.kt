package edu.ucne.tasktally.domain.repository

import edu.ucne.tasktally.domain.models.Tarea
import kotlinx.coroutines.flow.Flow

interface TareaRepository {
    fun observeTareas(): Flow<List<Tarea>>
    suspend fun getTarea(id: Int?): Tarea?
    suspend fun upsert(tarea: Tarea): Int
    suspend fun delete(tarea: Tarea)
    suspend fun deleteById(id: Int)
}