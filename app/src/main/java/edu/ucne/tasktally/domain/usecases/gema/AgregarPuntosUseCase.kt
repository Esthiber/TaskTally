package edu.ucne.tasktally.domain.usecases.gema

import edu.ucne.tasktally.domain.repository.GemaRepository
import edu.ucne.tasktally.data.remote.Resource
import javax.inject.Inject

class AgregarPuntosUseCase @Inject constructor(
    private val gemaRepository: GemaRepository
) {
    suspend operator fun invoke(gemaId: Int, puntosGanados: Double): Resource<Unit> {
        return try {
            val gema = gemaRepository.getGema(gemaId)
            if (gema == null) {
                return Resource.Error("Gema no encontrada")
            }

            val gemaActualizada = gema.copy(
                puntosActuales = gema.puntosActuales + puntosGanados,
                puntosTotales = gema.puntosTotales + puntosGanados
            )

            gemaRepository.upsert(gemaActualizada)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("Error al agregar puntos a la gema: ${e.message}")
        }
    }
}