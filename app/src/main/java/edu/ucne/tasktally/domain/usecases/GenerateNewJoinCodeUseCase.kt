package edu.ucne.tasktally.domain.usecases

import edu.ucne.tasktally.domain.repository.ZonaRepository
import javax.inject.Inject

class GenerateNewJoinCodeUseCase @Inject constructor(
    private val repository: ZonaRepository
) {
    suspend operator fun invoke(zonaId: Int): String =
        repository.generateNewJoinCode(zonaId)
}
