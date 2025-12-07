package edu.ucne.tasktally.domain.usecases

import edu.ucne.tasktally.domain.models.TareaMentor
import edu.ucne.tasktally.domain.repository.TareaMentorRepository
import javax.inject.Inject

class UpsertTareaMentorUseCase @Inject constructor(
    private val repo: TareaMentorRepository
) {
    suspend operator fun invoke(tarea: TareaMentor): String = repo.upsert(tarea)
}
