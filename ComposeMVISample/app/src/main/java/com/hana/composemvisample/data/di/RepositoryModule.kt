package com.hana.composemvisample.data.di

import com.hana.composemvisample.data.datasource.GithubDataSource
import com.hana.composemvisample.data.repository.GithubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun providesGithubRepository(githubDataSource: GithubDataSource): GithubRepository =
        GithubRepository(githubDataSource)
}