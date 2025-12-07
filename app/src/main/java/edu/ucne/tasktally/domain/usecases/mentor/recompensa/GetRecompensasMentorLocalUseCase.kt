package edu.ucne.tasktally.domain.usecases.mentor.recompensa

import edu.ucne.tasktally.domain.models.RecompensaMentor
import edu.ucne.tasktally.domain.repository.RecompensaMentorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecompensasMentorLocalUseCase @Inject constructor(
    private val repository: RecompensaMentorRepository
) {
    operator fun invoke(): Flow<List<RecompensaMentor>> {
        return repository.observeRecompensasMentor()
    }
}