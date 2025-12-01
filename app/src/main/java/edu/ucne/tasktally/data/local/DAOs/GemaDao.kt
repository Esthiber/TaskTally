package edu.ucne.tasktally.data.local.DAOs

import androidx.room.*
import edu.ucne.tasktally.data.local.entidades.GemaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GemaDao {
    @Query("SELECT * FROM gema ORDER BY gemaId DESC")
    fun observeAll(): Flow<List<GemaEntity>>

    @Query("SELECT * FROM gema WHERE gemaId = :id")
    suspend fun getById(id: Int?): GemaEntity?

    @Upsert
    suspend fun upsert(gema: GemaEntity)

    @Delete
    suspend fun delete(gema: GemaEntity)

    @Query("DELETE FROM gema WHERE gemaId = :id")
    suspend fun deleteById(id: Int)
}