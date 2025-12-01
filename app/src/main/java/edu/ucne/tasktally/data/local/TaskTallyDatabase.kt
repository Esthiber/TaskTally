package edu.ucne.tasktally.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.tasktally.data.local.DAOs.EstadoDao
import edu.ucne.tasktally.data.local.DAOs.PersonaDao
import edu.ucne.tasktally.data.local.DAOs.PersonaRecompensaDao
import edu.ucne.tasktally.data.local.DAOs.RecompensaDao
import edu.ucne.tasktally.data.local.DAOs.TareaDao
import edu.ucne.tasktally.data.local.DAOs.UsuarioDao
import edu.ucne.tasktally.data.local.entidades.EstadoEntity
import edu.ucne.tasktally.data.local.entidades.PersonaEntity
import edu.ucne.tasktally.data.local.entidades.PersonaRecompensaEntity
import edu.ucne.tasktally.data.local.entidades.RecompensaEntity
import edu.ucne.tasktally.data.local.entidades.TareaEntity
import edu.ucne.tasktally.data.local.entidades.UsuarioEntity

@Database(
    entities = [
        UsuarioEntity::class,
        EstadoEntity::class,
        PersonaEntity::class,
        PersonaRecompensaEntity::class,
        RecompensaEntity::class,
        TareaEntity::class],
    version = 3,
    exportSchema = false
)
abstract class TaskTallyDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun estadoDao(): EstadoDao
    abstract fun personaDao(): PersonaDao
    abstract fun personaRecompensaDao(): PersonaRecompensaDao
    abstract fun recompensaDao(): RecompensaDao
    abstract fun tareaDao(): TareaDao
}
