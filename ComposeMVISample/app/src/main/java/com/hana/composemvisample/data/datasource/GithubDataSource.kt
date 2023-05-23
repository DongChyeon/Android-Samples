package com.hana.composemvisample.data.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hana.composemvisample.data.ResultWrapper
import com.hana.composemvisample.data.api.GithubService
import com.hana.composemvisample.data.di.DispatcherModule
import com.hana.composemvisample.data.model.Repo
import com.hana.composemvisample.data.model.RepoSearchResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GithubDataSource @Inject constructor(
    private val githubService: GithubService,
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    fun searchRepos(
        query: String,
        perPage: Int,
        page: Int
    ): Flow<ResultWrapper<RepoSearchResult>> = flow {
        githubService.searchRepos(query, perPage, page).onSuccess {
            emit(ResultWrapper.Success(it))
        }.onFailure {
            emit(ResultWrapper.Error(it.message!!))
        }
    }.flowOn(ioDispatcher)

}