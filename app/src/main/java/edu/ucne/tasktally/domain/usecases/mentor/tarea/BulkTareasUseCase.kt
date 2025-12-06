package edu.ucne.tasktally.domain.usecases.mentor.tarea

import edu.ucne.tasktally.data.remote.DTOs.mentor.tareas.BulkTareasRequest
import edu.ucne.tasktally.data.remote.DTOs.mentor.tareas.BulkTareasResponse
import edu.ucne.tasktally.data.remote.Resource
import edu.ucne.tasktally.data.remote.TaskTallyApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BulkTareasUseCase @Inject constructor(
    private val api: TaskTallyApi
) {
    operator fun invoke(request: BulkTareasRequest): Flow<Resource<BulkTareasResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.bulkTareas(request)
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