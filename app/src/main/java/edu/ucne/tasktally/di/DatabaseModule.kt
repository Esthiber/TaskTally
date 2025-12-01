package edu.ucne.tasktally.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.tasktally.data.local.DAOs.EstadoDao
import edu.ucne.tasktally.data.local.DAOs.PersonaDao
import edu.ucne.tasktally.data.local.DAOs.PersonaRecompensaDao
import edu.ucne.tasktally.data.local.DAOs.RecompensaDao
import edu.ucne.tasktally.data.local.DAOs.TareaDao
import edu.ucne.tasktally.data.local.TaskTallyDatabase
import edu.ucne.tasktally.data.local.DAOs.UsuarioDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideTaskTallyDatabase(@ApplicationContext context: Context): TaskTallyDatabase {
        return Room.databaseBuilder(
            context,
            TaskTallyDatabase::class.java,
            "TaskTally.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideUsuarioDao(database: TaskTallyDatabase): UsuarioDao {
        return database.usuarioDao()
    }

    @Provides
    fun provideEstadoDao(database: TaskTallyDatabase): EstadoDao {
        return database.estadoDao()
    }

    @Provides
    fun providePersonaDao(database: TaskTallyDatabase): PersonaDao {
        return database.personaDao()
    }

    @Provides
    fun provideRecompensaDao(database: TaskTallyDatabase): RecompensaDao {
        return database.recompensaDao()
    }

    @Provides
    fun provideTareaDao(database: TaskTallyDatabase): TareaDao {
        return database.tareaDao()
    }

    @Provides
    fun providePersonaRecompensaDao(database: TaskTallyDatabase): PersonaRecompensaDao {
        return database.personaRecompensaDao()
    }

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }
}
