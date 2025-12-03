package edu.ucne.tasktally.data.remote

import edu.ucne.tasktally.data.remote.DTOs.usuarios.UsuarioRequest
import edu.ucne.tasktally.data.remote.DTOs.usuarios.UsuarioResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsuarioApi {
    @GET("api/Usuarios")
    suspend fun getUsuarios(): Response<List<UsuarioResponse>>

    @POST("api/Usuarios")
    suspend fun createUsuario(@Body request: UsuarioRequest): Response<UsuarioResponse>

    @PUT("api/Usuarios/{id}")
    suspend fun updateUsuario(@Path("id") id: Int, @Body request: UsuarioRequest): Response<Unit>
}