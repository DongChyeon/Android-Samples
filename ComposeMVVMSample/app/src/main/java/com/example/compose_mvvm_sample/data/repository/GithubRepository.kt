package com.example.compose_mvvm_sample.data.repository

import com.example.compose_mvvm_sample.data.Result
import com.example.compose_mvvm_sample.data.datasource.GithubDataSource
import com.example.compose_mvvm_sample.data.model.RepoSearchResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val githubDataSource: GithubDataSource
) {
    fun searchRepos(query: String): Flow<Result<RepoSearchResult>> =
        githubDataSource.searchRepos(query)
}