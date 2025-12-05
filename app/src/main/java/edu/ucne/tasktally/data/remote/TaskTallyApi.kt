package edu.ucne.tasktally.data.remote

import edu.ucne.tasktally.data.remote.DTOs.auth.*
import edu.ucne.tasktally.data.remote.DTOs.recompensa.*
import edu.ucne.tasktally.data.remote.DTOs.tareas.*
import edu.ucne.tasktally.data.remote.DTOs.zone.*
import retrofit2.Response
import retrofit2.http.*

interface TaskTallyApi {

    // auth

    @POST("api/Auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("api/Auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @POST("api/Auth/refresh")
    suspend fun refreshToken(
        @Body request: RefreshTokenRequest
    ): Response<RefreshTokenResponse>

    @POST("api/Auth/logout")
    suspend fun logout(
        @Body request: LogoutRequest
    ): Response<Unit>

    @GET("api/Auth/validate")
    suspend fun validateToken(): Response<Unit>

    @GET("api/Auth/me")
    suspend fun getCurrentUser(): Response<UserInfo>

    // gemas

    @POST("api/Gemas/join-zone")
    suspend fun joinZone(
        @Body request: JoinZoneRequest
    ): Response<Unit>

    @POST("api/Gemas/leave-zone")
    suspend fun leaveZone(
        @Body request: LeaveZoneRequest
    ): Response<Unit>

    @GET("api/Gemas/{gemaId}/tareas")
    suspend fun getTareasGema(
        @Path("gemaId") gemaId: Int
    ): Response<List<TareaGemaResponse>>

    @GET("api/Gemas/{gemaId}/recompensas")
    suspend fun getRecompensasGema(
        @Path("gemaId") gemaId: Int
    ): Response<List<RecompensaDto>>

    @PUT("api/Gemas/{gemaId}/tareas/bulk-update-estado")
    suspend fun bulkUpdateEstadoTareas(
        @Path("gemaId") gemaId: Int,
        @Body requests: List<UpdateTareaEstadoRequest>
    ): Response<BulkUpdateTareasResponse>

    @POST("api/Gemas/canjear-recompensa")
    suspend fun canjearRecompensa(
        @Body request: CanjearRecompensaRequest
    ): Response<CanjearRecompensaResponse>

    // mentor - tareas

    @POST("api/Mentors/{mentorId}/tareas")
    suspend fun createTarea(
        @Path("mentorId") mentorId: Int,
        @Body request: CreateTareaRequest
    ): Response<TareaDto>

    @PUT("api/Mentors/{mentorId}/tareas/{tareasGroupId}")
    suspend fun updateTarea(
        @Path("mentorId") mentorId: Int,
        @Path("tareasGroupId") tareasGroupId: Int,
        @Body request: UpdateTareaRequest
    ): Response<TareaDto>

    @DELETE("api/Mentors/{mentorId}/tareas/{tareasGroupId}")
    suspend fun deleteTarea(
        @Path("mentorId") mentorId: Int,
        @Path("tareasGroupId") tareasGroupId: Int
    ): Response<Unit>

    @POST("api/Mentors/tareas/bulk")
    suspend fun bulkTareas(
        @Body request: BulkTareasRequest
    ): Response<BulkTareasResponse>

    // mentor - recompensas

    @POST("api/Mentors/{mentorId}/recompensas")
    suspend fun createRecompensa(
        @Path("mentorId") mentorId: Int,
        @Body request: CreateRecompensaRequest
    ): Response<RecompensaDto>

    @PUT("api/Mentors/{mentorId}/recompensas/{recompensaId}")
    suspend fun updateRecompensa(
        @Path("mentorId") mentorId: Int,
        @Path("recompensaId") recompensaId: Int,
        @Body request: UpdateRecompensaRequest
    ): Response<RecompensaDto>

    @DELETE("api/Mentors/{mentorId}/recompensas/{recompensaId}")
    suspend fun deleteRecompensa(
        @Path("mentorId") mentorId: Int,
        @Path("recompensaId") recompensaId: Int
    ): Response<Unit>

    @POST("api/Mentors/recompensas/bulk")
    suspend fun bulkRecompensas(
        @Body request: BulkRecompensasRequest
    ): Response<BulkRecompensasResponse>

    // mentor - zone

    @POST("api/Mentors/{mentorId}/zone/update-code")
    suspend fun updateZoneCode(
        @Path("mentorId") mentorId: Int
    ): Response<Unit>

    @PUT("api/Mentors/{mentorId}/zone/name")
    suspend fun updateZoneName(
        @Path("mentorId") mentorId: Int,
        @Body request: UpdateZoneRequest
    ): Response<Unit>
}