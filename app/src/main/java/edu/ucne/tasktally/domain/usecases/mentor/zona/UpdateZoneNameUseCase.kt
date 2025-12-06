package edu.ucne.tasktally.domain.usecases.mentor.zona

import edu.ucne.tasktally.data.remote.DTOs.mentor.zone.UpdateZoneRequest
import edu.ucne.tasktally.data.remote.Resource
import edu.ucne.tasktally.data.remote.TaskTallyApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateZoneNameUseCase @Inject constructor(
    private val api: TaskTallyApi
) {
    operator fun invoke(mentorId: Int, zoneName: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.updateZoneName(mentorId, UpdateZoneRequest(zoneName))
            if (response.isSuccessful) {
                emit(Resource.Success(Unit))
            } else {
                emit(Resource.Error("Error al actualizar nombre de zona: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error desconocido"))
        }
    }
}