package edu.ucne.tasktally.domain.usecases.mentor.recompensa

import edu.ucne.tasktally.data.remote.DTOs.mentor.recompensa.BulkRecompensasRequest
import edu.ucne.tasktally.data.remote.DTOs.mentor.recompensa.BulkRecompensasResponse
import edu.ucne.tasktally.data.remote.Resource
import edu.ucne.tasktally.data.remote.TaskTallyApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class BulkRecompensasUseCase @Inject constructor(
    private val api: TaskTallyApi
) {
    operator fun invoke(request: BulkRecompensasRequest): Flow<Resource<BulkRecompensasResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.bulkRecompensas(request)
            if (response.isSuccessful && response.body() != null) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Error("Error en operación bulk: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error desconocido"))
        }
    }
}