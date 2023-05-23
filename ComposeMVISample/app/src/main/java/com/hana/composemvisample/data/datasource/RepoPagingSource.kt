package com.hana.composemvisample.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hana.composemvisample.data.model.Repo
import com.hana.composemvisample.data.repository.GithubRepository
import kotlinx.coroutines.flow.first


class RepoPagingSource(
    private val githubRepository: GithubRepository,
    private val query: String
) : PagingSource<Int, Repo>() {

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        return state.anchorPosition?.let { achorPosition ->
            state.closestPageToPosition(achorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(achorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        val position = params.key ?: INIT_PAGE_INDEX
        val loadData =
            githubRepository.searchRepos(
                query = query,
                page = position,
                perPage = params.loadSize,
            ).first()
        try {
            loadData.isSuccess { result ->
                return LoadResult.Page(
                    data = result.items,
                    prevKey = if (position == INIT_PAGE_INDEX) null else position - 1,
                    nextKey = if (result.items.isEmpty()) null else position + 1,
                )
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
        return LoadResult.Error(IllegalArgumentException("페이징 데이터를 불러올 수 없습니다."))
    }

    companion object {
        const val INIT_PAGE_INDEX = 1
        const val PAGE_SIZE = 10
    }
}