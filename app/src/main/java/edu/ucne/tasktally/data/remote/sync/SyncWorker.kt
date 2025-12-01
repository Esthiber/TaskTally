package edu.ucne.tasktally.data.remote.sync

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import edu.ucne.tasktally.data.remote.Resource
import edu.ucne.tasktally.domain.repository.UsuarioRepository

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val usuarioRepository: UsuarioRepository
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        return when (usuarioRepository.postPendingUsuarios()) {
            is Resource.Success -> Result.success()
            is Resource.Error -> Result.retry()
            else -> Result.failure()
        }
    }
}