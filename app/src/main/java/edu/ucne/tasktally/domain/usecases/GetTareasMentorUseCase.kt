package edu.ucne.tasktally.domain.usecases

import edu.ucne.tasktally.domain.models.TareaMentor
import edu.ucne.tasktally.domain.repository.TareaMentorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTareasMentorUseCase @Inject constructor(
    private val repo: TareaMentorRepository
) {
    operator fun invoke(): Flow<List<TareaMentor>> = repo.observeTareasMentor()
}
