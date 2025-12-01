package edu.ucne.tasktally.data.local.DAOs

import androidx.room.*
import edu.ucne.tasktally.data.local.entidades.ProgresoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgresoDao {
    @Query("SELECT * FROM progreso ORDER BY progresoId DESC")
    fun observeAll(): Flow<List<ProgresoEntity>>

    @Query("SELECT * FROM progreso WHERE progresoId = :id")
    suspend fun getById(id: Int?): ProgresoEntity?

    @Upsert
    suspend fun upsert(progreso: ProgresoEntity)

    @Delete
    suspend fun delete(progreso: ProgresoEntity)

    @Query("DELETE FROM progreso WHERE progresoId = :id")
    suspend fun deleteById(id: Int)
}