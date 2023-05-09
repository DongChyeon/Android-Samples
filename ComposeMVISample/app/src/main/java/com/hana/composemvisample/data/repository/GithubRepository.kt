package com.hana.composemvisample.data.repository

import com.hana.composemvisample.data.ResultWrapper
import com.hana.composemvisample.data.datasource.GithubDataSource
import com.hana.composemvisample.data.model.RepoSearchResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val githubDataSource: GithubDataSource
) {
    fun searchRepos(query: String): Flow<ResultWrapper<RepoSearchResult>> =
        githubDataSource.searchRepos(query)
}