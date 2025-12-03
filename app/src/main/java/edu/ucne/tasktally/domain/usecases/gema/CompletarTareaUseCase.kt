package edu.ucne.tasktally.domain.usecases.gema

import edu.ucne.tasktally.domain.repository.GemaRepository
import edu.ucne.tasktally.domain.repository.TareaRepository
import edu.ucne.tasktally.domain.models.Tarea
import edu.ucne.tasktally.data.remote.Resource
import javax.inject.Inject

class CompletarTareaUseCase @Inject constructor(
    private val tareaRepository: TareaRepository,
    private val gemaRepository: GemaRepository
) {
    suspend operator fun invoke(tareaId: String, gemaId: String, puntosGanados: Double): Resource<Unit> {
        return try {
            // Get the task
            val tarea = tareaRepository.getTarea(tareaId)
            if (tarea == null) {
                return Resource.Error("Tarea no encontrada")
            }
            
            // Mark task as completed
            val tareaActualizada = tarea.copy(estado = "Completada")
            tareaRepository.upsert(tareaActualizada)

            // Update gema points
            val gema = gemaRepository.getGema(gemaId)
            gema?.let {
                val total = it.puntosActuales + puntosGanados
                gemaRepository.upsert(it.copy(puntosActuales = total))
            }

            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("Error al completar tarea: ${e.message}")
        }
    }
}