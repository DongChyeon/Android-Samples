package com.hana.composemvisample.data.datasource

import com.hana.composemvisample.data.ResultWrapper
import com.hana.composemvisample.data.api.GithubService
import com.hana.composemvisample.data.model.RepoSearchResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GithubDataSource @Inject constructor(
    private val githubService: GithubService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    fun searchRepos(query: String): Flow<ResultWrapper<RepoSearchResult>> = flow {
        githubService.searchRepos(query).onSuccess {
            emit(ResultWrapper.Success(it))
        }.onFailure {
            emit(ResultWrapper.Error(it.message!!))
        }
    }.flowOn(ioDispatcher)
}