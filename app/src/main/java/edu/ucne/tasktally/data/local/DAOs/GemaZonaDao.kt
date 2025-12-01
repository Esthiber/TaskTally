package edu.ucne.tasktally.data.local.DAOs

import androidx.room.*
import edu.ucne.tasktally.data.local.entidades.GemaZonaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GemaZonaDao {
    @Query("SELECT * FROM gemaZona ORDER BY gemaZonaId DESC")
    fun observeAll(): Flow<List<GemaZonaEntity>>

    @Query("SELECT * FROM gemaZona WHERE gemaZonaId = :id")
    suspend fun getById(id: Int?): GemaZonaEntity?

    @Upsert
    suspend fun upsert(gemaZona: GemaZonaEntity)

    @Delete
    suspend fun delete(gemaZona: GemaZonaEntity)

    @Query("DELETE FROM gemaZona WHERE gemaZonaId = :id")
    suspend fun deleteById(id: Int)
}