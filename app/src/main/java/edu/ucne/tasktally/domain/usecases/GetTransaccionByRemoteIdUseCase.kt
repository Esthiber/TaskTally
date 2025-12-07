package edu.ucne.tasktally.domain.usecases

import edu.ucne.tasktally.domain.models.Transaccion
import edu.ucne.tasktally.domain.repository.TransaccionRepository
import javax.inject.Inject

class GetTransaccionByRemoteIdUseCase @Inject constructor(
    private val repo: TransaccionRepository
) {
    suspend operator fun invoke(remoteId: Int?): Transaccion? = repo.getTransaccionByRemoteId(remoteId)
}
