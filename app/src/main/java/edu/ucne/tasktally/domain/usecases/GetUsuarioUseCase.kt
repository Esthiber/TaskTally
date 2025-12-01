package edu.ucne.tasktally.domain.usecases

import edu.ucne.tasktally.domain.models.Usuario
import edu.ucne.tasktally.domain.repository.UsuarioRepository
import javax.inject.Inject

class GetUsuarioUseCase @Inject constructor(
    private val repo: UsuarioRepository
) {
    suspend operator fun invoke(id: String): Usuario? = repo.getUsuario(id)
}
