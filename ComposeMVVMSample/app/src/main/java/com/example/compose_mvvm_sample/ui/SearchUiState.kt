package com.example.compose_mvvm_sample.ui

import com.example.compose_mvvm_sample.data.model.Repo

data class SearchUiState(
    val state: UiState = UiState.NONE,
    val repos: List<Repo> = listOf()
)

enum class UiState {
    NONE, LOADING, SUCCESS, ERROR
}
