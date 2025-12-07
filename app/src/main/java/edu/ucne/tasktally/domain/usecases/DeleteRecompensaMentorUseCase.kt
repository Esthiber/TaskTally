package edu.ucne.tasktally.domain.usecases

import edu.ucne.tasktally.domain.models.RecompensaMentor
import edu.ucne.tasktally.domain.repository.RecompensaMentorRepository
import javax.inject.Inject

class DeleteRecompensaMentorUseCase @Inject constructor(
    private val repo: RecompensaMentorRepository
) {
    suspend operator fun invoke(recompensa: RecompensaMentor) = repo.delete(recompensa)
}
