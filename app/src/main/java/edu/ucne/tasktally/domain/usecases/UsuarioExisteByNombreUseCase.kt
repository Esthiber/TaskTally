package edu.ucne.tasktally.domain.usecases

import edu.ucne.tasktally.domain.repository.UsuarioRepository
import javax.inject.Inject

class UsuarioExisteByNombreUseCase @Inject constructor(
    private val repo: UsuarioRepository
) {
    suspend operator fun invoke(userName: String, idUsuarioActual: String? = null): Boolean {
        return repo.usuarioExisteByNombre(userName, idUsuarioActual)
    }
}
