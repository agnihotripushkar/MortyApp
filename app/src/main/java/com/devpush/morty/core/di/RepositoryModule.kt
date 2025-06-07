package com.devpush.morty.core.di

import com.devpush.morty.features.allepisodes.data.repository.EpisodesRepositoryImpl
import com.devpush.morty.features.allepisodes.domain.repository.EpisodesRepository
import com.devpush.morty.features.characterdetails.data.repository.CharacterRepositoryImpl
import com.devpush.morty.features.characterdetails.domain.repository.CharacterRepository
import com.devpush.network.KtorClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providesCharacterRepository(ktorClient: KtorClient): CharacterRepository {
        return CharacterRepositoryImpl(ktorClient)
    }

    @Provides
    @Singleton
    fun providesEpisodeRepository(ktorClient: KtorClient): EpisodesRepository {
        return EpisodesRepositoryImpl(ktorClient)

    }
}