package edu.ucne.tasktally.data.local.DAOs

import androidx.room.*
import edu.ucne.tasktally.data.local.entidades.TransaccionRecompensaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransaccionRecompensaDao {
    @Query("SELECT * FROM transaccionRecompensa ORDER BY transaccionRecompensaId DESC")
    fun observeAll(): Flow<List<TransaccionRecompensaEntity>>

    @Query("SELECT * FROM transaccionRecompensa WHERE transaccionRecompensaId = :id")
    suspend fun getById(id: Int?): TransaccionRecompensaEntity?

    @Upsert
    suspend fun upsert(entity: TransaccionRecompensaEntity)

    @Delete
    suspend fun delete(entity: TransaccionRecompensaEntity)

    @Query("DELETE FROM transaccionRecompensa WHERE transaccionRecompensaId = :id")
    suspend fun deleteById(id: Int)
}