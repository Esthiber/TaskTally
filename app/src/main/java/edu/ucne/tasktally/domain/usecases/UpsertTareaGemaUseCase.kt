package edu.ucne.tasktally.domain.usecases

import edu.ucne.tasktally.domain.models.TareaGema
import edu.ucne.tasktally.domain.repository.TareaGemaRepository
import javax.inject.Inject

class UpsertTareaGemaUseCase @Inject constructor(
    private val repo: TareaGemaRepository
) {
    suspend operator fun invoke(tarea: TareaGema): String = repo.upsert(tarea)
}
