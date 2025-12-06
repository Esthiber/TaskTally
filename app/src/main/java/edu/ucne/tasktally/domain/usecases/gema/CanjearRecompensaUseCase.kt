package edu.ucne.tasktally.domain.usecases.gema

import edu.ucne.tasktally.data.remote.DTOs.gema.recompensa.CanjearRecompensaRequest
import edu.ucne.tasktally.data.remote.DTOs.gema.recompensa.CanjearRecompensaResponse
import edu.ucne.tasktally.data.remote.Resource
import edu.ucne.tasktally.data.remote.TaskTallyApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CanjearRecompensaUseCase @Inject constructor(
    private val api: TaskTallyApi
) {
    operator fun invoke(gemaId: Int, recompensaId: Int): Flow<Resource<CanjearRecompensaResponse>> =
        flow {
            emit(Resource.Loading())
            try {
                val response = api.canjearRecompensa(CanjearRecompensaRequest(gemaId, recompensaId))
                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    if (body.success) {
                        emit(Resource.Success(body))
                    } else {
                        emit(Resource.Error(body.message))
                    }
                } else {
                    emit(Resource.Error("Error al canjear recompensa: ${response.message()}"))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Error desconocido"))
            }
        }
}