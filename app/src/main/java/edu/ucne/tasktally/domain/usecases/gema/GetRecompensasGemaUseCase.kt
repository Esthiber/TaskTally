package edu.ucne.tasktally.domain.usecase.gema

import edu.ucne.tasktally.data.remote.Resource
import edu.ucne.tasktally.data.remote.TaskTallyApi
import edu.ucne.tasktally.data.remote.DTOs.gema.recompensa.RecompensasGemaResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRecompensasGemaUseCase @Inject constructor(
    private val api: TaskTallyApi
) {
    operator fun invoke(gemaId: Int): Flow<Resource<List<RecompensasGemaResponse>>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getRecompensasGema(gemaId)
            if (response.isSuccessful && response.body() != null) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Error("Error al obtener recompensas: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error desconocido"))
        }
    }
}