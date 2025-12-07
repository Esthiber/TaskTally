package edu.ucne.tasktally.domain.usecases

import edu.ucne.tasktally.domain.models.Zona
import edu.ucne.tasktally.domain.repository.ZonaRepository
import javax.inject.Inject

class UpsertZonaUseCase @Inject constructor(
    private val repository: ZonaRepository
) {
    suspend operator fun invoke(zona: Zona): Int = repository.upsert(zona)
}
