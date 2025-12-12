package edu.ucne.tasktally.data.repositories

import edu.ucne.tasktally.data.local.DAOs.ZonaDao
import edu.ucne.tasktally.data.local.DAOs.mentor.MentorDao
import edu.ucne.tasktally.data.local.entidades.mentor.RecompensaMentorEntity
import edu.ucne.tasktally.data.local.entidades.mentor.TareaMentorEntity
import edu.ucne.tasktally.data.mappers.toDomain
import edu.ucne.tasktally.data.mappers.toEntity
import edu.ucne.tasktally.data.mappers.toRecompensaMentorDomain
import edu.ucne.tasktally.data.mappers.toRecompensaMentorEntity
import edu.ucne.tasktally.data.mappers.toTareaMentorDomain
import edu.ucne.tasktally.data.mappers.toTareaMentorEntity
import edu.ucne.tasktally.data.mappers.toZonaDomain
import edu.ucne.tasktally.data.remote.DTOs.mentor.recompensa.BulkRecompensasRequest
import edu.ucne.tasktally.data.remote.DTOs.mentor.recompensa.BulkRecompensasResponse
import edu.ucne.tasktally.data.remote.DTOs.mentor.recompensa.RecompensaOperationDto
import edu.ucne.tasktally.data.remote.DTOs.mentor.tareas.BulkTareasRequest
import edu.ucne.tasktally.data.remote.DTOs.mentor.tareas.BulkTareasResponse
import edu.ucne.tasktally.data.remote.DTOs.mentor.tareas.TareaOperationDto
import edu.ucne.tasktally.data.remote.DTOs.mentor.zone.UpdateZoneCodeResponse
import edu.ucne.tasktally.data.remote.DTOs.mentor.zone.UpdateZoneRequest
import edu.ucne.tasktally.data.remote.DTOs.mentor.zone.UpdateZoneResponse
import edu.ucne.tasktally.data.remote.Resource
import edu.ucne.tasktally.data.remote.TaskTallyApi
import edu.ucne.tasktally.data.util.RepositoryConstants
import edu.ucne.tasktally.di.IoDispatcher
import edu.ucne.tasktally.domain.models.RecompensaMentor
import edu.ucne.tasktally.domain.models.TareaMentor
import edu.ucne.tasktally.domain.models.Zona
import edu.ucne.tasktally.domain.repository.MentorRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class MentorRepositoryImpl @Inject constructor(
    private val mentorDao: MentorDao,
    private val zonaDao: ZonaDao,
    private val api: TaskTallyApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : MentorRepository {

    //region Tareas
    override fun observeTareas(): Flow<List<TareaMentor>> =
        mentorDao.observeTodasLasTareasMentor().map { list ->
            list.map { it.toTareaMentorDomain() }
        }

    override suspend fun getTareaById(id: String): TareaMentor? = withContext(ioDispatcher) {
        mentorDao.obtenerTareaMentorPorId(id)?.toTareaMentorDomain()
    }

    override suspend fun createTareaLocal(tarea: TareaMentor) = withContext(ioDispatcher) {
        val tareaToSave = if (tarea.remoteId != null) {
            tarea.copy(isPendingCreate = false, isPendingUpdate = true)
        } else {
            tarea
        }
        mentorDao.upsertTarea(tareaToSave.toTareaMentorEntity())
    }

    override suspend fun updateTareaLocal(tarea: TareaMentor) = withContext(ioDispatcher) {
        mentorDao.upsertTarea(tarea.copy(isPendingUpdate = true).toTareaMentorEntity())
    }

    override suspend fun deleteTareaLocal(tarea: TareaMentor) = withContext(ioDispatcher) {
        mentorDao.upsertTarea(tarea.copy(isPendingDelete = true).toTareaMentorEntity())
    }

    override suspend fun deleteAllTareasLocal(mentorId: Int) = withContext(ioDispatcher) {
        mentorDao.eliminarTodasLasTareas()
    }
    //endregion

    //region Recompensas
    override fun observeRecompensas(): Flow<List<RecompensaMentor>> =
        mentorDao.observeTodasLasRecompensasMentor().map { list ->
            list.map { it.toRecompensaMentorDomain() }
        }

    override suspend fun getRecompensaById(id: String): RecompensaMentor? =
        withContext(ioDispatcher) {
        mentorDao.obtenerRecompensaMentorPorId(id)?.toRecompensaMentorDomain()
        }

    override suspend fun createRecompensaLocal(recompensa: RecompensaMentor) =
        withContext(ioDispatcher) {
        val recompensaToSave = if (recompensa.remoteId != null) {
            recompensa.copy(isPendingCreate = false, isPendingUpdate = true)
        } else {
            recompensa
        }
        mentorDao.upsertRecompensa(recompensaToSave.toRecompensaMentorEntity())
    }

    override suspend fun updateRecompensaLocal(recompensa: RecompensaMentor) =
        withContext(ioDispatcher) {
        mentorDao.upsertRecompensa(
            recompensa.copy(isPendingUpdate = true).toRecompensaMentorEntity()
        )
    }

    override suspend fun deleteRecompensaLocal(recompensa: RecompensaMentor) =
        withContext(ioDispatcher) {
        mentorDao.upsertRecompensa(
            recompensa.copy(isPendingDelete = true).toRecompensaMentorEntity()
        )
    }

    override suspend fun deleteAllRecompensasLocal() = withContext(ioDispatcher) {
        val list = mentorDao.observeTodasLasRecompensasMentor().first()
        list.forEach {
            mentorDao.upsertRecompensa(
                it.copy(isPendingDelete = true)
            )
        }
    }
    //endregion

    //region zona
    override suspend fun getZoneInfo(mentorId: Int, zoneId: Int): Zona {
        return withContext(ioDispatcher) {
            try {
                val response = api.obtenerMentorInfoZona(mentorId, zoneId)
                if (response.isSuccessful) {
                    response.body()?.let { zoneInfo ->
                        val zona = zoneInfo.toZonaDomain().copy(zonaId = zoneId)

                        zonaDao.upsert(zona.toEntity())
                        zona
                    } ?: run {
                        getLocalZoneInfo(zoneId)
                    }
                } else {
                    getLocalZoneInfo(zoneId)
                }
            } catch (e: Exception) {
                getLocalZoneInfo(zoneId)
            }
        }
    }

    private suspend fun getLocalZoneInfo(zoneId: Int): Zona = withContext(ioDispatcher) {
        zonaDao.getById(zoneId)?.toDomain() ?: Zona(
            zonaId = zoneId,
            nombre = "",
            joinCode = "",
            mentorId = "",
            gemas = emptyList()
        )
    }

    override suspend fun updateZoneCode(mentorId: Int): Resource<UpdateZoneCodeResponse> =
        withContext(ioDispatcher) {
            return@withContext try {
            val response = api.updateZoneCode(mentorId)
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.Success(it)
                } ?: Resource.Error(RepositoryConstants.ERROR_NULL_RESPONSE_BODY)
            } else {
                Resource.Error(
                    "${RepositoryConstants.ERROR_UPDATE_ZONE_CODE}${
                        response.errorBody()?.string()
                    }"
                )
            }
        } catch (e: Exception) {
            Resource.Error("${RepositoryConstants.ERROR_EXCEPTION_OCCURRED}${e.message}")
        }
    }

    override suspend fun updateZoneName(
        mentorId: Int,
        zoneName: String
    ): Resource<UpdateZoneResponse> = withContext(ioDispatcher) {
        return@withContext try {
            val request = UpdateZoneRequest(zoneName)
            val response = api.updateZoneName(mentorId, request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.Success(it)
                } ?: Resource.Error(RepositoryConstants.ERROR_NULL_RESPONSE_BODY)
            } else {
                Resource.Error(
                    "${RepositoryConstants.ERROR_UPDATE_ZONE_NAME}${
                        response.errorBody()?.string()
                    }"
                )
            }
        } catch (e: Exception) {
            Resource.Error("${RepositoryConstants.ERROR_EXCEPTION_OCCURRED}${e.message}")
        }
    }
    //endregion

    override suspend fun getTareasRecompensasMentor(mentorId: Int): Resource<Unit> =
        withContext(ioDispatcher) {
        if (mentorId == 0) {
            return@withContext Resource.Error(RepositoryConstants.ERROR_NO_MENTOR_ID)
        }
            return@withContext try {
            val response = api.getTareasRecompensasMentor(mentorId)
            if (response.isSuccessful) {

                mentorDao.eliminarTodasLasTareas()
                mentorDao.eliminarTodasLasRecompensas()

                response.body()?.let { mentorData ->
                    mentorData.tareas.forEach { tareaDto ->
                        val tarea = TareaMentor(
                            tareaId = UUID.randomUUID().toString(),
                            remoteId = tareaDto.tareasGroupId ?: 0,

                            titulo = tareaDto.titulo ?: "",
                            descripcion = tareaDto.descripcion ?: "",
                            puntos = tareaDto.puntos ?: 0,
                            dias = tareaDto.dias ?: "",
                            repetir = tareaDto.repetir ?: 0,
                            nombreImgVector = tareaDto.nombreImgVector ?: "",

                            isPendingCreate = false,
                            isPendingUpdate = false,
                            isPendingDelete = false
                        )
                        mentorDao.upsertTarea(tarea.toTareaMentorEntity())
                    }
                    mentorData.recompensas.forEach { recompensaDto ->
                        val recompensa = RecompensaMentor(
                            recompensaId = UUID.randomUUID().toString(),
                            remoteId = recompensaDto.recompensaId ?: 0,

                            titulo = recompensaDto.titulo ?: "",
                            descripcion = recompensaDto.descripcion ?: "",
                            precio = recompensaDto.precio ?: 0,
                            isDisponible = recompensaDto.isDisponible ?: true,
                            nombreImgVector = recompensaDto.nombreImgVector ?: "",

                            isPendingCreate = false,
                            isPendingUpdate = false,
                            isPendingDelete = false
                        )
                        mentorDao.upsertRecompensa(recompensa.toRecompensaMentorEntity())
                    }
                    Resource.Success(Unit)
                } ?: Resource.Error(RepositoryConstants.ERROR_NULL_RESPONSE_BODY)
            } else {
                Resource.Error(
                    "${RepositoryConstants.ERROR_FETCH_TAREAS_RECOMPENSAS}${
                        response.errorBody()?.string()
                    }"
                )
            }
        } catch (e: Exception) {
            Resource.Error("${RepositoryConstants.ERROR_EXCEPTION_OCCURRED}${e.message}")
        }
    }

    override suspend fun postPendingTareas(
        zoneId: Int,
        mentorId: Int
    ): Resource<BulkTareasResponse> = withContext(ioDispatcher) {
        if (mentorId == 0)
            return@withContext Resource.Error(RepositoryConstants.ERROR_NO_MENTOR_ID)

        return@withContext try {
            val (pendingCreateTareas, pendingUpdateTareas, pendingDeleteTareas, allOperations) = createOperationList() // Contiene llamadas a DAO

            if (allOperations.isEmpty()) {
                return@withContext Resource.Success(BulkTareasResponse(0, 0, emptyList()))
            }

            val bulkRequest = BulkTareasRequest(
                mentorId = mentorId,
                zoneId = zoneId,
                tareas = allOperations
            )
            val response = api.bulkTareas(bulkRequest)

            if (response.isSuccessful) {
                response.body()?.let { bulkResponse ->
                    handleBulkResponse(
                        bulkResponse,
                        pendingCreateTareas,
                        pendingUpdateTareas,
                        pendingDeleteTareas
                    )
                    Resource.Success(bulkResponse)
                } ?: Resource.Error(RepositoryConstants.ERROR_NULL_RESPONSE_BODY)
            } else {
                Resource.Error(
                    "${RepositoryConstants.ERROR_API_CALL_FAILED} ${response.errorBody()?.string()}"
                )
            }
        } catch (e: Exception) {
            Resource.Error("${RepositoryConstants.ERROR_EXCEPTION_OCCURRED} ${e.message}")
        }
    }

    private data class TareaOperationData(
        val pendingCreateTareas: List<TareaMentorEntity>,
        val pendingUpdateTareas: List<TareaMentorEntity>,
        val pendingDeleteTareas: List<TareaMentorEntity>,
        val allOperations: List<TareaOperationDto>
    )

    private suspend fun createOperationList(): TareaOperationData = withContext(ioDispatcher) {
        val pendingCreateTareas = mentorDao.obtenerTareasPendientesDeCrear()
        val pendingUpdateTareas = mentorDao.obtenerTareasPendientesDeActualizar()
        val pendingDeleteTareas = mentorDao.obtenerTareasPendientesDeEliminar()

        val allOperations = mutableListOf<TareaOperationDto>()


        pendingCreateTareas.forEach { tarea ->
            allOperations.add(createOperationDto("create", tarea))
        }

        pendingUpdateTareas.forEach { tarea ->
            tarea.remoteId?.let {
                allOperations.add(createOperationDto("update", tarea))
            }
        }

        pendingDeleteTareas.forEach { tarea ->
            tarea.remoteId?.let {
                allOperations.add(createOperationDto("delete", tarea, true))
            }
        }

        return@withContext TareaOperationData(
            pendingCreateTareas,
            pendingUpdateTareas,
            pendingDeleteTareas,
            allOperations
        )
    }

    private fun createOperationDto(
        accion: String,
        tarea: TareaMentorEntity,
        isDelete: Boolean = false
    ): TareaOperationDto {
        return TareaOperationDto(
            accion = accion,
            tareasGroupId = if (isDelete || accion == "update") tarea.remoteId else null,
            titulo = if (!isDelete) tarea.titulo else null,
            descripcion = if (!isDelete) tarea.descripcion else null,
            puntos = if (!isDelete) tarea.puntos else null,
            repetir = if (!isDelete) tarea.repetir else null,
            dias = if (!isDelete) tarea.dias else null,
            nombreImgVector = if (!isDelete) tarea.nombreImgVector else null
        )
    }

    private suspend fun handleBulkResponse(
        bulkResponse: BulkTareasResponse,
        pendingCreateTareas: List<TareaMentorEntity>,
        pendingUpdateTareas: List<TareaMentorEntity>,
        pendingDeleteTareas: List<TareaMentorEntity>
    ) = withContext(ioDispatcher) {
        bulkResponse.results.forEach { result ->
            if (result.success && result.tareasGroupId != null) {
                when (result.accion) {
                    "create" -> {
                        val localTarea = pendingCreateTareas.find { it.titulo == result.titulo }
                        localTarea?.let {
                            mentorDao.upsertTarea(
                                it.copy(
                                    remoteId = result.tareasGroupId,
                                    isPendingCreate = false
                                )
                            )
                        }
                    }

                    "update" -> {
                        val localTarea =
                            pendingUpdateTareas.find { it.remoteId == result.tareasGroupId }
                        localTarea?.let {
                            mentorDao.upsertTarea(it.copy(isPendingUpdate = false))
                        }
                    }

                    "delete" -> {
                        val localTarea =
                            pendingDeleteTareas.find { it.remoteId == result.tareasGroupId }
                        localTarea?.let {
                            mentorDao.eliminarTarea(it)
                        }
                    }
                }
            }
        }
    }

    override suspend fun postPendingRecompensas(
        zoneId: Int,
        mentorId: Int
    ): Resource<BulkRecompensasResponse> = withContext(ioDispatcher) {
        if (mentorId == 0)
            return@withContext Resource.Error(RepositoryConstants.ERROR_NO_MENTOR_ID)

        return@withContext try {
            val (pendingCreate, pendingUpdate, pendingDelete, allOperations) = createRecompensaOperationList()

            if (allOperations.isEmpty())
                return@withContext Resource.Success(BulkRecompensasResponse(0, 0, emptyList()))

            val bulkRequest = BulkRecompensasRequest(
                mentorId = mentorId,
                zoneId = zoneId,
                recompensas = allOperations
            )
            val response = api.bulkRecompensas(bulkRequest)

            if (response.isSuccessful) {
                response.body()?.let { bulkResponse ->
                    handleBulkRecompensaResponse(
                        bulkResponse,
                        pendingCreate,
                        pendingUpdate,
                        pendingDelete
                    )
                    Resource.Success(bulkResponse)
                } ?: Resource.Error(RepositoryConstants.ERROR_NULL_RESPONSE_BODY)
            } else {
                Resource.Error(
                    "${RepositoryConstants.ERROR_API_CALL_FAILED}${response.errorBody()?.string()}"
                )
            }
        } catch (e: Exception) {
            Resource.Error("${RepositoryConstants.ERROR_EXCEPTION_OCCURRED}${e.message}")
        }
    }

    private data class RecompensaOperationData(
        val pendingCreateRecompensas: List<RecompensaMentorEntity>,
        val pendingUpdateRecompensas: List<RecompensaMentorEntity>,
        val pendingDeleteRecompensas: List<RecompensaMentorEntity>,
        val allOperations: List<RecompensaOperationDto>
    )

    private suspend fun createRecompensaOperationList(): RecompensaOperationData =
        withContext(ioDispatcher) {
        val pendingCreateRecompensas = mentorDao.obtenerRecompensasPendientesDeCrear()
        val pendingUpdateRecompensas = mentorDao.obtenerRecompensasPendientesDeActualizar()
        val pendingDeleteRecompensas = mentorDao.obtenerRecompensasPendientesDeEliminar()

        val allOperations = mutableListOf<RecompensaOperationDto>()

        pendingCreateRecompensas.forEach { recompensa ->
            allOperations.add(createRecompensaOperationDto("create", recompensa))
        }

        pendingUpdateRecompensas.forEach { recompensa ->
            recompensa.remoteId?.let {
                allOperations.add(createRecompensaOperationDto("update", recompensa))
            }
        }

        pendingDeleteRecompensas.forEach { recompensa ->
            recompensa.remoteId?.let {
                allOperations.add(createRecompensaOperationDto("delete", recompensa, true))
            }
        }

            return@withContext RecompensaOperationData(
            pendingCreateRecompensas,
            pendingUpdateRecompensas,
            pendingDeleteRecompensas,
            allOperations
        )
    }

    private fun createRecompensaOperationDto(
        accion: String,
        recompensa: RecompensaMentorEntity,
        isDelete: Boolean = false
    ): RecompensaOperationDto {
        return RecompensaOperationDto(
            accion = accion,
            recompensaId = if (isDelete || accion == "update") recompensa.remoteId else null,
            titulo = if (!isDelete) recompensa.titulo else null,
            descripcion = if (!isDelete) recompensa.descripcion else null,
            precio = if (!isDelete) recompensa.precio else null,
            isDisponible = if (!isDelete) recompensa.isDisponible else null,
            nombreImgVector = if (!isDelete) recompensa.nombreImgVector else null
        )
    }

    private suspend fun handleBulkRecompensaResponse(
        bulkResponse: BulkRecompensasResponse,
        pendingCreateRecompensas: List<RecompensaMentorEntity>,
        pendingUpdateRecompensas: List<RecompensaMentorEntity>,
        pendingDeleteRecompensas: List<RecompensaMentorEntity>
    ) = withContext(ioDispatcher) {
        bulkResponse.results.forEach { result ->
            if (result.success && result.recompensaId != null) {
                when (result.accion) {
                    "create" -> {
                        val localRecompensa =
                            pendingCreateRecompensas.find { it.titulo == result.titulo }
                        localRecompensa?.let {
                            mentorDao.upsertRecompensa(
                                it.copy(
                                    remoteId = result.recompensaId,
                                    isPendingCreate = false
                                )
                            )
                        }
                    }

                    "update" -> {
                        val localRecompensa =
                            pendingUpdateRecompensas.find { it.remoteId == result.recompensaId }
                        localRecompensa?.let {
                            mentorDao.upsertRecompensa(it.copy(isPendingUpdate = false))
                        }
                    }

                    "delete" -> {
                        val localRecompensa =
                            pendingDeleteRecompensas.find { it.remoteId == result.recompensaId }
                        localRecompensa?.let {
                            mentorDao.eliminarRecompensaMentor(it)
                        }
                    }
                }
            }
        }
    }
}