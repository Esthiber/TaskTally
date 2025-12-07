package edu.ucne.tasktally.domain.usecases

import edu.ucne.tasktally.domain.models.RecompensaGema
import edu.ucne.tasktally.domain.repository.RecompensaGemaRepository
import javax.inject.Inject

class UpsertRecompensaGemaUseCase @Inject constructor(
    private val repo: RecompensaGemaRepository
) {
    suspend operator fun invoke(recompensa: RecompensaGema): String = repo.upsert(recompensa)
}
