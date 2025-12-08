package edu.ucne.tasktally.domain.repository

import edu.ucne.tasktally.data.remote.DTOs.gema.tarea.BulkUpdateTareasResponse
import edu.ucne.tasktally.data.remote.DTOs.mentor.tareas.BulkTareasResponse
import edu.ucne.tasktally.data.remote.Resource
import edu.ucne.tasktally.domain.models.RecompensaGema
import edu.ucne.tasktally.domain.models.TareaGema
import kotlinx.coroutines.flow.Flow

interface GemaRepository {
    //region Tareas
    fun observeTareas(): Flow<List<TareaGema>>
    suspend fun IniciarTareaGema(tareaId: String)
    suspend fun FinalizarTareaGema(tareaId: String)
    //endregion

    //region Recompensas
    fun observeRecompensas(): Flow<List<RecompensaGema>>
    suspend fun CanjearRecompensa(recompensaId: String)
    //endregion

    suspend fun postPendingEstadosTareas(): Resource<BulkUpdateTareasResponse>
    suspend fun postPendingCanjearRecompensas(): Resource<Unit>

}