package edu.ucne.tasktally.domain.usecases

import edu.ucne.tasktally.domain.models.RecompensaMentor
import edu.ucne.tasktally.domain.repository.RecompensaMentorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecompensasMentorUseCase @Inject constructor(
    private val repo: RecompensaMentorRepository
) {
    operator fun invoke(): Flow<List<RecompensaMentor>> = repo.observeRecompensasMentor()
}
