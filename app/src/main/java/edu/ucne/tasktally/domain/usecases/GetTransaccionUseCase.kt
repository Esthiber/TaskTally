package edu.ucne.tasktally.domain.usecases

import edu.ucne.tasktally.domain.models.Transaccion
import edu.ucne.tasktally.domain.repository.TransaccionRepository
import javax.inject.Inject

class GetTransaccionUseCase @Inject constructor(
    private val repo: TransaccionRepository
) {
    suspend operator fun invoke(id: String?): Transaccion? = repo.getTransaccion(id)
}
