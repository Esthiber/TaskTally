package edu.ucne.tasktally.domain.usecases

import edu.ucne.tasktally.domain.models.TareaMentor
import edu.ucne.tasktally.domain.repository.TareaMentorRepository
import javax.inject.Inject

class GetTareaMentorByRemoteIdUseCase @Inject constructor(
    private val repo: TareaMentorRepository
) {
    suspend operator fun invoke(remoteId: Int): TareaMentor? = repo.getTareaMentorByRemoteId(remoteId)
}
