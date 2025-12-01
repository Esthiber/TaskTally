package edu.ucne.tasktally.data.remote.repository

import android.util.Log
import edu.ucne.tasktally.data.local.DAOs.UsuarioDao
import edu.ucne.tasktally.data.mappers.toDomain
import edu.ucne.tasktally.data.mappers.toEntity
import edu.ucne.tasktally.data.remote.RemoteDataSource
import edu.ucne.tasktally.data.remote.Resource
import edu.ucne.tasktally.data.remote.usuarios.UsuarioRequest
import edu.ucne.tasktally.domain.models.Usuario
import edu.ucne.tasktally.domain.repository.UsuarioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UsuariosRepositoryImpl @Inject constructor(
    private val localDataSource: UsuarioDao,
    private val remoteDataSource: RemoteDataSource
): UsuarioRepository {
    override fun observeUsuarios(): Flow<List<Usuario>> = localDataSource.ObserveAll().map { list ->
        list.map { it.toDomain() }
    }

    override suspend fun getUsuarioById(id: String?): Usuario? =
        localDataSource.getById(id)?.toDomain()

    override suspend fun getUsuario(id: String): Usuario? =
        localDataSource.getUsuario(id)?.toDomain()

    override suspend fun createUsuarioLocal(usuario: Usuario): Resource<Usuario> {
        val pending = usuario.copy(isPendingCreate = true)
        localDataSource.upsert(pending.toEntity())
        Log.d("UsuariosRepository", "Usuario creado localmente, sync programado")

        return Resource.Success(pending)
    }

    override suspend fun upsert(usuario: Usuario): Resource<Unit> {
        localDataSource.upsert(usuario.toEntity())

        val remoteId = usuario.remoteId ?: return Resource.Success(Unit)
        val req = UsuarioRequest(userName = usuario.userName, password = usuario.password)
        return when (val result = remoteDataSource.updateUsuario(remoteId, req)) {
            is Resource.Success -> {
                Log.d("UsuariosRepository", "Usuario updated successfully on server")
                Resource.Success(Unit)
            }
            is Resource.Error -> {
                Log.w("UsuariosRepository", "Failed to update on server, but saved locally: ${result.message}")
                Resource.Success(Unit) // Still success because saved locally
            }
            else -> Resource.Success(Unit)
        }
    }


    override suspend fun usuarioExisteByNombre(userName: String, idUsuarioActual: String?): Boolean {
        return localDataSource.existByName(userName, idUsuarioActual)
    }

    override suspend fun postPendingUsuarios(): Resource<Unit> {

        val pending = localDataSource.getPendingCreateUsuarios()
        Log.d("SyncUsuarios", "Found ${pending.size} pending usuarios to sync")

        if (pending.isEmpty()) {
            return Resource.Success(Unit)
        }

        for (usuario in pending) {
            val req = UsuarioRequest(usuario.userName, usuario.password)
            Log.d("SyncUsuarios", "Sincronizando usuario ${req.userName}")
            when (val result = remoteDataSource.createUsuario(req)) {
                is Resource.Success -> {
                    val usuarioId = result.data?.usuarioId
                    if (usuarioId == null) {
                        Log.e("SyncUsuarios", "API no devolvio usuarioId para ${usuario.userName}")
                        return Resource.Error("API no devolvio el ID del usuario creado")
                    }
                    Log.d("SyncUsuarios", "Usuario sincronizado con ID: $usuarioId")
                    val synced = usuario.copy(remoteId = usuarioId, isPendingCreate = false)
                    localDataSource.upsert(synced)
                    Log.d("SyncUsuarios", "Sincronizado usuario ${usuario.userName} con remoteId: $usuarioId")
                }
                is Resource.Error -> {
                    Log.e("SyncUsuarios", "Fallo la sincronizacion de ${usuario.id} ${usuario.userName} ${result.message}")
                    return Resource.Error("Fallo la sincronizacion")
                }
                else -> {
                    Log.e("SyncUsuarios", "Error desconocido al sincronizar ${usuario.id} ${usuario.userName}")
                }
            }
        }
        return Resource.Success(Unit)
    }

}