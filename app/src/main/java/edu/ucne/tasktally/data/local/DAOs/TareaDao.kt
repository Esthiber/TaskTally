package edu.ucne.tasktally.data.local.DAOs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.tasktally.data.local.entidades.TareaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TareaDao {
    @Query("SELECT * FROM tareas ORDER BY tareaId DESC")
    fun observeAll(): Flow<List<TareaEntity>>

    @Query("SELECT * FROM tareas WHERE tareaId = :id")
    suspend fun getById(id: Int?): TareaEntity?

    @Query("SELECT * FROM tareas WHERE estadoId = :estadoId")
    fun observeByEstado(estadoId: Int): Flow<List<TareaEntity>>

    @Upsert
    suspend fun upsert(tarea: TareaEntity)

    @Delete
    suspend fun delete(tarea: TareaEntity)

    @Query("DELETE FROM tareas WHERE tareaId = :id")
    suspend fun deleteById(id: Int)
}