package edu.ucne.tasktally.domain.usecases.gema

import edu.ucne.tasktally.data.remote.DTOs.gema.tarea.BulkUpdateTareasResponse
import edu.ucne.tasktally.data.remote.DTOs.gema.tarea.UpdateTareaEstadoRequest
import edu.ucne.tasktally.data.remote.Resource
import edu.ucne.tasktally.data.remote.TaskTallyApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateEstadoTareasUseCase @Inject constructor(
    private val api: TaskTallyApi
) {
    operator fun invoke( gemaId: Int,  tareas: List<UpdateTareaEstadoRequest>
    ): Flow<Resource<BulkUpdateTareasResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.bulkUpdateEstadoTareas(gemaId, tareas)
            if (response.isSuccessful && response.body() != null) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Error("Error al actualizar tareas: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error desconocido"))
        }
    }
}