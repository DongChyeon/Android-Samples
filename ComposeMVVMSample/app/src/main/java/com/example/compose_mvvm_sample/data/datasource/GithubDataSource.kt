package com.example.compose_mvvm_sample.data.datasource

import com.example.compose_mvvm_sample.data.Result
import com.example.compose_mvvm_sample.data.api.GithubService
import com.example.compose_mvvm_sample.data.model.RepoSearchResult
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
    fun searchRepos(query: String): Flow<Result<RepoSearchResult>> = flow {
        val result = githubService.searchRepos(query)
        if (result.isSuccessful) {
            result.body()?.let {
                emit(Result.Success(it))
            }
        } else {
            result.errorBody()?.let {
                emit(Result.Error(it.toString()))
            }
        }
    }.flowOn(ioDispatcher)
}