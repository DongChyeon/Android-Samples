package com.hana.composemvisample.ui

import com.hana.composemvisample.data.model.Repo

sealed interface MainEvent {
    object Loading: MainEvent
    data class ShowRepos(val repos: List<Repo>): MainEvent
    data class ShowError(val error: String): MainEvent
}