package edu.ucne.tasktally.data.local.DAOs

import androidx.room.*
import edu.ucne.tasktally.data.local.entidades.UserInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserInfoDao {
    @Query("SELECT * FROM userInfo ORDER BY userInfoId DESC")
    fun observeAll(): Flow<List<UserInfoEntity>>

    @Query("SELECT * FROM userInfo WHERE userInfoId = :id")
    suspend fun getById(id: Int?): UserInfoEntity?

    @Upsert
    suspend fun upsert(userInfo: UserInfoEntity)

    @Delete
    suspend fun delete(userInfo: UserInfoEntity)

    @Query("DELETE FROM userInfo WHERE userInfoId = :id")
    suspend fun deleteById(id: Int)
}