package edu.ucne.tasktally.domain.usecases.mentor.tarea

import edu.ucne.tasktally.data.remote.DTOs.mentor.TareaDto
import edu.ucne.tasktally.data.remote.DTOs.mentor.tareas.CreateTareaRequest
import edu.ucne.tasktally.data.remote.Resource
import edu.ucne.tasktally.data.remote.TaskTallyApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateTareaUseCase @Inject constructor(
    private val api: TaskTallyApi
) {
    operator fun invoke(mentorId: Int, request: CreateTareaRequest): Flow<Resource<TareaDto>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.createTarea(mentorId, request)
            if (response.isSuccessful && response.body() != null) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Error("Error al crear tarea: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error desconocido"))
        }
    }
}