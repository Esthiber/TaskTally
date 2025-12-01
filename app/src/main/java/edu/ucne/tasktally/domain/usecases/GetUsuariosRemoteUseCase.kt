package edu.ucne.tasktally.domain.usecases

import edu.ucne.tasktally.data.remote.RemoteDataSource
import edu.ucne.tasktally.data.remote.usuarios.UsuarioResponse
import javax.inject.Inject

class GetUsuariosRemoteUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend operator fun invoke(): List<UsuarioResponse> {
        return remoteDataSource.getUsuarios()
    }
}
