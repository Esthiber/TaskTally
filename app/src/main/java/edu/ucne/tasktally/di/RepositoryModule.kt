package edu.ucne.tasktally.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.tasktally.data.repositories.GemaRepositoryImpl
import edu.ucne.tasktally.data.repositories.MentorRepositoryImpl
import edu.ucne.tasktally.data.repositories.RecompensaRepositoryImpl
import edu.ucne.tasktally.data.repositories.TareaRepositoryImpl
import edu.ucne.tasktally.data.repositories.ZonaRepositoryImpl
import edu.ucne.tasktally.domain.repository.GemaRepository
import edu.ucne.tasktally.domain.repository.MentorRepository
import edu.ucne.tasktally.domain.repository.RecompensaRepository
import edu.ucne.tasktally.domain.repository.TareaRepository
import edu.ucne.tasktally.domain.repository.ZonaRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTareaRepository(
        impl: TareaRepositoryImpl
    ): TareaRepository

    @Binds
    @Singleton
    abstract fun bindRecompensaRepository(
        impl: RecompensaRepositoryImpl
    ): RecompensaRepository

    @Binds
    @Singleton
    abstract fun bindGemaRepository(
        impl: GemaRepositoryImpl
    ): GemaRepository

    @Binds
    @Singleton
    abstract fun bindMentorRepository(
        impl: MentorRepositoryImpl
    ): MentorRepository

    @Binds
    @Singleton
    abstract fun bindZonaRepository(
        impl: ZonaRepositoryImpl
    ): ZonaRepository
}