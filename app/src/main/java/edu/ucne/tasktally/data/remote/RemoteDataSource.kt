package edu.ucne.tasktally.data.remote

import android.util.Log
import edu.ucne.tasktally.data.remote.DTOs.mentor.recompensa.BulkRecompensasRequest
import edu.ucne.tasktally.data.remote.DTOs.mentor.tareas.BulkTareasRequest
import edu.ucne.tasktally.data.remote.DTOs.mentor.tareas.BulkTareasResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api: TaskTallyApi
) {
    suspend fun postTareas(
        tareas: BulkTareasRequest
    ) : BulkTareasResponse {

        return TODO("Provide the return value")
    }
}