package com.hana.composemvisample.ui

import androidx.paging.PagingData
import com.hana.composemvisample.data.model.Repo
import kotlinx.coroutines.flow.Flow

sealed interface MainEvent {
    object Loading : MainEvent
    data class ShowReposPaging(val repos: Flow<PagingData<Repo>>) : MainEvent
    data class ShowError(val error: String) : MainEvent
}