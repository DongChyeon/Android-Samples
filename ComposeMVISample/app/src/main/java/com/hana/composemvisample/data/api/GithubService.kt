package com.hana.composemvisample.data.api

import com.hana.composemvisample.data.model.RepoSearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @GET("search/repositories")
    suspend fun searchRepos(
        @Query("q") query: String
    ): Result<RepoSearchResult>
}