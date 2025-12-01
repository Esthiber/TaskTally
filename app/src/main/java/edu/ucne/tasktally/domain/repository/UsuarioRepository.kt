package edu.ucne.tasktally.domain.repository

import edu.ucne.tasktally.data.remote.Resource
import edu.ucne.tasktally.domain.models.Usuario
import kotlinx.coroutines.flow.Flow

interface UsuarioRepository {
    fun observeUsuarios(): Flow<List<Usuario>>
    suspend fun getUsuarioById(id: String?): Usuario?
    suspend fun getUsuario(id: String): Usuario?
    suspend fun createUsuarioLocal(usuario: Usuario): Resource<Usuario>
    suspend fun upsert(usuario: Usuario): Resource<Unit>
    suspend fun usuarioExisteByNombre(userName: String, idUsuarioActual: String?): Boolean
    suspend fun postPendingUsuarios(): Resource<Unit>
}