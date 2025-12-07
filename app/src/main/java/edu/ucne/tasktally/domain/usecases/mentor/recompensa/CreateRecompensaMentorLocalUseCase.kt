package edu.ucne.tasktally.domain.usecases.mentor.recompensa

import edu.ucne.tasktally.domain.models.RecompensaMentor
import edu.ucne.tasktally.domain.repository.RecompensaMentorRepository
import javax.inject.Inject


class CreateRecompensaMentorLocalUseCase @Inject constructor(
    private val repository: RecompensaMentorRepository
) {
    suspend operator fun invoke(recompensa: RecompensaMentor): String {
        val recompensaPendiente = recompensa.copy(isPendingCreate = true)
        return repository.upsert(recompensaPendiente)
    }
}