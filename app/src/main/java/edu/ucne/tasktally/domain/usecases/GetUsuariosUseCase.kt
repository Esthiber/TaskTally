package edu.ucne.tasktally.domain.usecases

import edu.ucne.tasktally.domain.models.Usuario
import edu.ucne.tasktally.domain.repository.UsuarioRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsuariosUseCase @Inject constructor(
    private val repo: UsuarioRepository
) {
    operator fun invoke(): Flow<List<Usuario>> = repo.observeUsuarios()
}
