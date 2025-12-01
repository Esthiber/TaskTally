package edu.ucne.tasktally.data.local.DAOs

import androidx.room.*
import edu.ucne.tasktally.data.local.entidades.MentorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MentorDao {
    @Query("SELECT * FROM mentor ORDER BY mentorId DESC")
    fun observeAll(): Flow<List<MentorEntity>>

    @Query("SELECT * FROM mentor WHERE mentorId = :id")
    suspend fun getById(id: Int?): MentorEntity?

    @Upsert
    suspend fun upsert(mentor: MentorEntity)

    @Delete
    suspend fun delete(mentor: MentorEntity)

    @Query("DELETE FROM mentor WHERE mentorId = :id")
    suspend fun deleteById(id: Int)
}