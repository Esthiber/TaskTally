package edu.ucne.tasktally.domain.usecases.mentor.recompensa

import edu.ucne.tasktally.data.remote.Resource
import edu.ucne.tasktally.data.remote.TaskTallyApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteRecompensaUseCase @Inject constructor(
    private val api: TaskTallyApi
) {
    operator fun invoke(mentorId: Int, recompensaId: Int): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.deleteRecompensa(mentorId, recompensaId)
            if (response.isSuccessful) {
                emit(Resource.Success(Unit))
            } else {
                emit(Resource.Error("Error al eliminar recompensa: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error desconocido"))
        }
    }
}