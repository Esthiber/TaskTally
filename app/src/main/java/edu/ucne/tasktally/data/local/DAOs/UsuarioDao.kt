package edu.ucne.tasktally.data.local.DAOs

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.tasktally.data.local.entidades.UsuarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
    @Query(value = "SELECT * FROM usuarios ORDER BY id DESC")
    fun ObserveAll(): Flow<List<UsuarioEntity>>

    @Query("SELECT * FROM usuarios WHERE id = :id")
    suspend fun getById(id: String?): UsuarioEntity?

    @Query("SELECT * FROM usuarios WHERE id = :id")
    suspend fun getUsuario(id: String): UsuarioEntity?

    @Upsert
    suspend fun upsert(usuario: UsuarioEntity)


    @Query("SELECT * FROM usuarios WHERE isPendingCreate = 1")
    suspend fun getPendingCreateUsuarios(): List<UsuarioEntity>

    @Query("SELECT COUNT(*) > 0 FROM usuarios WHERE userName = :userName AND (:idUsuarioActual IS NULL OR id != :idUsuarioActual)")
    suspend fun existByName(userName: String, idUsuarioActual: String?): Boolean
}