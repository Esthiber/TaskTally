package edu.ucne.tasktally.domain.usecases

import edu.ucne.tasktally.domain.models.RecompensaGema
import edu.ucne.tasktally.domain.repository.RecompensaGemaRepository
import javax.inject.Inject

class GetRecompensaGemaByRemoteIdUseCase @Inject constructor(
    private val repo: RecompensaGemaRepository
) {
    suspend operator fun invoke(remoteId: Int?): RecompensaGema? = repo.getRecompensaGemaByRemoteId(remoteId)
}
