package edu.ucne.tasktally.domain.usecases.gema

import edu.ucne.tasktally.data.remote.DTOs.gema.zone.JoinZoneRequest
import edu.ucne.tasktally.data.remote.TaskTallyApi
import edu.ucne.tasktally.data.remote.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class JoinZoneUseCase @Inject constructor(
    private val api: TaskTallyApi
) {
    operator fun invoke(gemaId: Int, zoneCode: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.joinZone(JoinZoneRequest(gemaId, zoneCode))
            if (response.isSuccessful) {
                emit(Resource.Success(Unit))
            } else {
                emit(Resource.Error("Error al unirse a la zona: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error desconocido"))
        }
    }
}