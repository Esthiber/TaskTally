package edu.ucne.tasktally.domain.usecases.mentor.tarea

import edu.ucne.tasktally.domain.models.TareaMentor
import edu.ucne.tasktally.domain.repository.TareaMentorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTareasMentorLocalUseCase @Inject constructor(
    private val repository: TareaMentorRepository
) {
    operator fun invoke(): Flow<List<TareaMentor>> {
        return repository.observeTareasMentor()
    }
}
