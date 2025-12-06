package edu.ucne.tasktally.domain.usecases.mentor.recompensa

import edu.ucne.tasktally.data.remote.DTOs.mentor.recompensa.RecompensaDto
import edu.ucne.tasktally.data.remote.DTOs.mentor.recompensa.UpdateRecompensaRequest
import edu.ucne.tasktally.data.remote.Resource
import edu.ucne.tasktally.data.remote.TaskTallyApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateRecompensaUseCase @Inject constructor(
    private val api: TaskTallyApi
) {
    operator fun invoke(
        mentorId: Int, recompensaId: Int,
        request: UpdateRecompensaRequest
    ): Flow<Resource<RecompensaDto>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.updateRecompensa(mentorId, recompensaId, request)
            if (response.isSuccessful && response.body() != null) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Error("Error al actualizar recompensa: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error desconocido"))
        }
    }
}