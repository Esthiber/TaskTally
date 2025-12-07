package edu.ucne.tasktally.domain.usecases.mentor.tarea

import edu.ucne.tasktally.domain.models.TareaMentor
import edu.ucne.tasktally.domain.repository.TareaMentorRepository
import javax.inject.Inject

class CreateTareaMentorLocalUseCase @Inject constructor(
    private val repository: TareaMentorRepository
) {
    suspend operator fun invoke(tarea: TareaMentor): String {
        val tareaPendiente = tarea.copy(isPendingCreate = true)
        return repository.upsert(tareaPendiente)
    }
}
