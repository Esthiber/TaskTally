package edu.ucne.tasktally.domain.usecases.mentor

import edu.ucne.tasktally.data.remote.Resource
import edu.ucne.tasktally.domain.models.Tarea
import edu.ucne.tasktally.domain.repository.TareaRepository
import javax.inject.Inject

class AsignarTareaUseCase @Inject constructor(
    private val tareaRepository: TareaRepository
) {
    suspend operator fun invoke(tarea: Tarea): Resource<String> {
        return try {
            Resource.Success(tareaRepository.upsert(tarea))
        } catch (e: Exception) {
            Resource.Error("Error al asignar tarea: ${e.message}")
        }
    }
}