package edu.ucne.tasktally.domain.usecases.gema

import edu.ucne.tasktally.domain.models.Progreso
import edu.ucne.tasktally.domain.repository.ProgresoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetProgresoByGemaUseCase @Inject constructor(
    private val repository: ProgresoRepository
) {
    operator fun invoke(gemaId: Int): Flow<List<Progreso>> {
        return repository.observeProgresos()
            .map { list -> list.filter { it.gemaId == gemaId } }
    }
}