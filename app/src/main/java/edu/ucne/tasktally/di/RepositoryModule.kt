package edu.ucne.tasktally.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.tasktally.data.repositories.GemaRepositoryImpl
import edu.ucne.tasktally.data.repositories.MentorRepositoryImpl
import edu.ucne.tasktally.data.repositories.ZonaRepositoryImpl
import edu.ucne.tasktally.domain.repository.GemaRepository
import edu.ucne.tasktally.domain.repository.MentorRepository
import edu.ucne.tasktally.domain.repository.ZonaRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindGemaRepository(
        impl: GemaRepositoryImpl
    ): GemaRepository

    @Binds
    @Singleton
    fun bindMentorRepository(
        impl: MentorRepositoryImpl
    ): MentorRepository

    @Binds
    @Singleton
    fun bindZonaRepository(
        impl: ZonaRepositoryImpl
    ): ZonaRepository
}