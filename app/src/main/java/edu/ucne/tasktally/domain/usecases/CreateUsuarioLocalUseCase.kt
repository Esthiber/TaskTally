package edu.ucne.tasktally.domain.usecases

import edu.ucne.tasktally.data.remote.Resource
import edu.ucne.tasktally.domain.models.Usuario
import edu.ucne.tasktally.domain.repository.UsuarioRepository
import javax.inject.Inject

class CreateUsuarioLocalUseCase @Inject constructor(
    private val repo: UsuarioRepository
){
    suspend operator fun invoke(usuario: Usuario): Resource<Usuario> = repo.createUsuarioLocal(usuario)
}