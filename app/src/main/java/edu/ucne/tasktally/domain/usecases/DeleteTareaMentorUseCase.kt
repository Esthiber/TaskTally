package edu.ucne.tasktally.domain.usecases

import edu.ucne.tasktally.domain.models.TareaMentor
import edu.ucne.tasktally.domain.repository.TareaMentorRepository
import javax.inject.Inject

class DeleteTareaMentorUseCase @Inject constructor(
    private val repo: TareaMentorRepository
) {
    suspend operator fun invoke(tarea: TareaMentor) = repo.delete(tarea)
}
