package com.hana.composemvisample.data.di

import com.hana.composemvisample.data.api.GithubService
import com.hana.composemvisample.data.datasource.GithubDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Singleton
    @Provides
    fun providesGithubDataSource(
        githubService: GithubService,
        @DispatcherModule.IoDispatcher ioDispatcher: CoroutineDispatcher
    ): GithubDataSource =
        GithubDataSource(githubService, ioDispatcher)

}