package edu.ucne.tasktally.data.local.DAOs

import androidx.room.*
import edu.ucne.tasktally.data.local.entidades.RachaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RachaDao {
    @Query("SELECT * FROM racha ORDER BY rachaId DESC")
    fun observeAll(): Flow<List<RachaEntity>>

    @Query("SELECT * FROM racha WHERE rachaId = :id")
    suspend fun getById(id: Int?): RachaEntity?

    @Upsert
    suspend fun upsert(racha: RachaEntity)

    @Delete
    suspend fun delete(racha: RachaEntity)

    @Query("DELETE FROM racha WHERE rachaId = :id")
    suspend fun deleteById(id: Int)
}