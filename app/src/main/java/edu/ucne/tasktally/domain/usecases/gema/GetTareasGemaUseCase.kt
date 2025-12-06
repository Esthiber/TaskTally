package edu.ucne.tasktally.domain.usecase.gema

import edu.ucne.tasktally.data.remote.Resource
import edu.ucne.tasktally.data.remote.TaskTallyApi
import edu.ucne.tasktally.data.remote.DTOs.gema.tarea.TareasGemaResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTareasGemaUseCase @Inject constructor(
    private val api: TaskTallyApi
) {
    operator fun invoke(gemaId: Int): Flow<Resource<List<TareasGemaResponse>>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getTareasGema(gemaId)
            if (response.isSuccessful && response.body() != null) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Error("Error al obtener tareas: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error desconocido"))
        }
    }
}