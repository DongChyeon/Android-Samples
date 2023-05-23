package com.hana.composemvisample.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hana.composemvisample.data.ResultWrapper
import com.hana.composemvisample.data.datasource.GithubDataSource
import com.hana.composemvisample.data.datasource.RepoPagingSource
import com.hana.composemvisample.data.datasource.RepoPagingSource.Companion.INIT_PAGE_INDEX
import com.hana.composemvisample.data.datasource.RepoPagingSource.Companion.PAGE_SIZE
import com.hana.composemvisample.data.model.Repo
import com.hana.composemvisample.data.model.RepoSearchResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val githubDataSource: GithubDataSource
) {
    fun searchRepos(
        query: String,
        perPage: Int = PAGE_SIZE,
        page: Int = INIT_PAGE_INDEX
    ): Flow<ResultWrapper<RepoSearchResult>> =
        githubDataSource.searchRepos(query, perPage, page)

    fun searchReposPaging(
        query: String
    ): Flow<PagingData<Repo>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                RepoPagingSource(
                    githubRepository = this,
                    query = query,
                )
            },
        ).flow
    }
}