package edu.ucne.tasktally.domain.usecases

import edu.ucne.tasktally.domain.models.Transaccion
import edu.ucne.tasktally.domain.repository.TransaccionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTransaccionesUseCase @Inject constructor(
    private val repo: TransaccionRepository
) {
    operator fun invoke(): Flow<List<Transaccion>> = repo.observeTransacciones()
}
