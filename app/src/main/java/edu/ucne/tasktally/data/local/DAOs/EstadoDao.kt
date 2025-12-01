package edu.ucne.tasktally.data.local.DAOs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.tasktally.data.local.entidades.EstadoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EstadoDao {
    @Query("SELECT * FROM estado ORDER BY estadoId DESC")
    fun observeAll(): Flow<List<EstadoEntity>>

    @Query("SELECT * FROM estado WHERE estadoId = :id")
    suspend fun getById(id: Int?): EstadoEntity?

    @Upsert
    suspend fun upsert(estado: EstadoEntity)

    @Delete
    suspend fun delete(estado: EstadoEntity)

    @Query("DELETE FROM estado WHERE estadoId = :id")
    suspend fun deleteById(id: Int)
}
