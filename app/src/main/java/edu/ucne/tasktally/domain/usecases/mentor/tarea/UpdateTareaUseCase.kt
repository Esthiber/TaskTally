package edu.ucne.tasktally.domain.usecases.mentor.tarea

import edu.ucne.tasktally.data.remote.DTOs.mentor.TareaDto
import edu.ucne.tasktally.data.remote.DTOs.mentor.tareas.UpdateTareaRequest
import edu.ucne.tasktally.data.remote.Resource
import edu.ucne.tasktally.data.remote.TaskTallyApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class UpdateTareaUseCase @Inject constructor(
    private val api: TaskTallyApi
) {
    operator fun invoke(
        mentorId: Int, tareasGroupId: Int, request: UpdateTareaRequest
    ): Flow<Resource<TareaDto>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.updateTarea(mentorId, tareasGroupId, request)
            if (response.isSuccessful && response.body() != null) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Error("Error al actualizar tarea: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error desconocido"))
        }
    }
}