package edu.ucne.tasktally.domain.usecases

import edu.ucne.tasktally.domain.models.RecompensaMentor
import edu.ucne.tasktally.domain.repository.RecompensaMentorRepository
import javax.inject.Inject

class GetRecompensaMentorUseCase @Inject constructor(
    private val repo: RecompensaMentorRepository
) {
    suspend operator fun invoke(id: String?): RecompensaMentor? = repo.getRecompensaMentor(id)
}
