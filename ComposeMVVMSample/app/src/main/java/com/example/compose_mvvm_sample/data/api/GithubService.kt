package com.example.compose_mvvm_sample.data.api

import com.example.compose_mvvm_sample.data.model.RepoSearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @GET("search/repositories?sort=stars")
    suspend fun searchRepos(
        @Query("q") query: String
    ): Response<RepoSearchResult>
}