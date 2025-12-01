package edu.ucne.tasktally.data.remote.usuarios

import com.squareup.moshi.Json

data class UsuarioResponse(
    @Json(name = "usuarioId") val usuarioId: Int?,
    val userName: String,
    val password: String
)
