package edu.ucne.tasktally.domain.usecases.gema

import edu.ucne.tasktally.domain.models.Racha
import edu.ucne.tasktally.domain.repository.RachaRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class GetRachaUseCase @Inject constructor(
    private val repository: RachaRepository
) {
    suspend operator fun invoke(gemaId: Int): Racha? {
        return repository.observeRachas()
            .map { list -> list.firstOrNull { it.gemaId == gemaId } }
            .first()
    }
}