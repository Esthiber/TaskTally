package edu.ucne.tasktally.domain.usecases.mentor.recompensa

import edu.ucne.tasktally.data.remote.DTOs.mentor.recompensa.CreateRecompensaRequest
import edu.ucne.tasktally.data.remote.DTOs.mentor.recompensa.RecompensaDto
import edu.ucne.tasktally.data.remote.Resource
import edu.ucne.tasktally.data.remote.TaskTallyApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateRecompensaUseCase @Inject constructor(
    private val api: TaskTallyApi
) {
    operator fun invoke(mentorId: Int, request: CreateRecompensaRequest): Flow<Resource<RecompensaDto>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.createRecompensa(mentorId, request)
            if (response.isSuccessful && response.body() != null) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Error("Error al crear recompensa: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error desconocido"))
        }
    }
}