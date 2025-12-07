package edu.ucne.tasktally.domain.usecases

import edu.ucne.tasktally.domain.models.UserInfo
import edu.ucne.tasktally.domain.repository.UserInfoRepository
import javax.inject.Inject

class DeleteUserInfoUseCase @Inject constructor(
    private val repo: UserInfoRepository
) {
    suspend operator fun invoke(userInfo: UserInfo) = repo.delete(userInfo)
}
