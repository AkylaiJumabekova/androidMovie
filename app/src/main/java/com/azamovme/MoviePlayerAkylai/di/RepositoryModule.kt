package com.azamovme.MoviePlayerAkylai.di

import com.azamovme.MoviePlayerAkylai.repository.FavoriteRepository
import com.azamovme.MoviePlayerAkylai.repository.imp.FavoriteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
@InstallIn(SingletonComponent::class)
@ExperimentalCoroutinesApi
abstract class RepositoryModule {

    @Binds
    abstract fun bindAnimeRepository(repository: FavoriteRepositoryImpl): FavoriteRepository

}