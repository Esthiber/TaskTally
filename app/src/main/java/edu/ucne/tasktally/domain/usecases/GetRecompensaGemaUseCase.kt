package edu.ucne.tasktally.domain.usecases

import edu.ucne.tasktally.domain.models.RecompensaGema
import edu.ucne.tasktally.domain.repository.RecompensaGemaRepository
import javax.inject.Inject

class GetRecompensaGemaUseCase @Inject constructor(
    private val repo: RecompensaGemaRepository
) {
    suspend operator fun invoke(id: String?): RecompensaGema? = repo.getRecompensaGema(id)
}
