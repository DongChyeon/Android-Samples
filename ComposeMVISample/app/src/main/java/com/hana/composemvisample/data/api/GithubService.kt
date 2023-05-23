package com.hana.composemvisample.data.api

import com.hana.composemvisample.data.model.RepoSearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @GET("search/repositories")
    suspend fun searchRepos(
        @Query("q") query: String,
        @Query("per_page") perPage: Int = 10,
        @Query("page") page: Int = 1
    ): Result<RepoSearchResult>
}