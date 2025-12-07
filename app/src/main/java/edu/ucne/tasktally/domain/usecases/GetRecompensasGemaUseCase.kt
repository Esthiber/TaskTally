package edu.ucne.tasktally.domain.usecases

import edu.ucne.tasktally.domain.models.RecompensaGema
import edu.ucne.tasktally.domain.repository.RecompensaGemaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecompensasGemaUseCase @Inject constructor(
    private val repo: RecompensaGemaRepository
) {
    operator fun invoke(): Flow<List<RecompensaGema>> = repo.observeRecompensasGema()
}
