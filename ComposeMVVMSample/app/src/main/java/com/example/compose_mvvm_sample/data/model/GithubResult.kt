package com.example.compose_mvvm_sample.data.model

import com.google.gson.annotations.SerializedName

data class RepoSearchResult(
    @SerializedName("items") val items: List<Repo> = emptyList(),
)

data class Repo(
    @field:SerializedName("id") val id: Long,
    @field:SerializedName("full_name") val fullName: String,
    @field:SerializedName("description") val description: String?,
    @field:SerializedName("stargazers_count") val stars: Int,
    @field:SerializedName("forks_count") val forks: Int,
    @field:SerializedName("language") val language: String?
)