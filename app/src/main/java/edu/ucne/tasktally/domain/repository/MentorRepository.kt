package edu.ucne.tasktally.domain.repository

import edu.ucne.tasktally.data.remote.DTOs.mentor.tareas.BulkTareasResponse
import edu.ucne.tasktally.data.remote.DTOs.mentor.recompensa.BulkRecompensasResponse
import edu.ucne.tasktally.data.remote.DTOs.mentor.zone.UpdateZoneCodeResponse
import edu.ucne.tasktally.data.remote.DTOs.mentor.zone.UpdateZoneResponse
import edu.ucne.tasktally.data.remote.DTOs.mentor.zone.ZoneInfoMentorResponse
import edu.ucne.tasktally.data.remote.Resource
import edu.ucne.tasktally.domain.models.RecompensaMentor
import edu.ucne.tasktally.domain.models.TareaMentor
import kotlinx.coroutines.flow.Flow

interface MentorRepository {
    //region Tareas
    fun observeTareas(): Flow<List<TareaMentor>>
    suspend fun createTareaLocal(tarea: TareaMentor)
    suspend fun updateTareaLocal(tarea: TareaMentor)
    //endregion

    //region Recompensas
    fun observeRecompensas(): Flow<List<RecompensaMentor>>
    suspend fun createRecompensaLocal(recompensa: RecompensaMentor)
    suspend fun updateRecompensaLocal(recompensa: RecompensaMentor)
    //endregion

    //region zona
    suspend fun getZoneInfo(mentorId: Int): Resource<ZoneInfoMentorResponse>
    suspend fun updateZoneCode(mentorId: Int): Resource<UpdateZoneCodeResponse>
    suspend fun updateZoneName(zoneName: String): Resource<UpdateZoneResponse>
    //endregion

    suspend fun postPendingTareas(): Resource<BulkTareasResponse>
    suspend fun postPendingRecompensas(): Resource<BulkRecompensasResponse>

}