package edu.ucne.tasktally.domain.usecases.gema

import edu.ucne.tasktally.domain.repository.RachaRepository
import edu.ucne.tasktally.data.remote.Resource
import javax.inject.Inject

class ResetRachaUseCase @Inject constructor(
    private val repository: RachaRepository
) {
    suspend operator fun invoke(rachaId: Int): Resource<Unit> {
        return try {
            val racha = repository.getRacha(rachaId)
            if (racha != null) {
                repository.upsert(racha.copy(dias = 0))
                Resource.Success(Unit)
            } else {
                Resource.Error("Racha no encontrada")
            }
        } catch (e: Exception) {
            Resource.Error("Error al reiniciar racha: ${e.message}")
        }
    }
}