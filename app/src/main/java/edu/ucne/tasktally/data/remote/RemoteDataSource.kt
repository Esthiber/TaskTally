package edu.ucne.tasktally.data.remote

import android.util.Log
import edu.ucne.tasktally.data.remote.usuarios.UsuarioRequest
import edu.ucne.tasktally.data.remote.usuarios.UsuarioResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api: UsuarioApi
){
    suspend fun getUsuarios(): List<UsuarioResponse> {
        return try {
            val response = api.getUsuarios()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                Log.e("RemoteDataSource", "Error getting usuarios: ${response.code()} ${response.message()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("RemoteDataSource", "Exception getting usuarios", e)
            emptyList()
        }
    }

    suspend fun createUsuario(req: UsuarioRequest): Resource<UsuarioResponse> {
        return try {
            Log.d("RemoteDataSource", "Enviando request: userName=${req.userName}")
            val response = api.createUsuario(req)
            Log.d("RemoteDataSource", "Respuesta HTTP: code=${response.code()}, isSuccessful=${response.isSuccessful}")
            if (response.isSuccessful) {
                var body = response.body()
                Log.d("RemoteDataSource", "Body recibido: usuarioId=${body?.usuarioId}, userName=${body?.userName}")

                if (body != null && body.usuarioId == null) {
                    val location = response.headers()["Location"]
                    Log.d("RemoteDataSource", "Location header: $location")

                    val idFromLocation = location?.let { obtenerIdLocation(it) }
                    if (idFromLocation != null) {
                        Log.d("RemoteDataSource", "ID extraido del Location: $idFromLocation")
                        body = body.copy(usuarioId = idFromLocation)
                    }
                }

                body?.let {
                    Resource.Success(it)
                } ?: Resource.Error("Respuesta vacia del servidor")
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("RemoteDataSource", "Error HTTP ${response.code()}: ${response.message()}, body: $errorBody")
                Resource.Error("HTTP ${response.code()}: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("RemoteDataSource", "Excepcion al crear usuario", e)
            Resource.Error("Error de red: ${e.localizedMessage ?: "Ocurrio un error desconocido"}")
        }
    }

    private fun obtenerIdLocation(location: String): Int? {
        return try {
            val regex = """[?&]id=(\d+)""".toRegex()
            val matchResult = regex.find(location)
            matchResult?.groupValues?.get(1)?.toIntOrNull()
        } catch (e: Exception) {
            Log.e("RemoteDataSource", "Error al extraer ID del Location", e)
            null
        }
    }

    suspend fun updateUsuario(id: Int, req: UsuarioRequest): Resource<Unit> {
        return try {
            val response = api.updateUsuario(id, req)
            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error("HTTP ${response.code()}: ${response.message()}")
            }
        } catch (e: Exception) {
            return Resource.Error("Error de red: ${e.localizedMessage ?: "Ocurrio un error desconocido"}")
        }
    }

}