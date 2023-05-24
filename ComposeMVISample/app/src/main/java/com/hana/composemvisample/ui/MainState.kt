package com.hana.composemvisample.ui

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.hana.composemvisample.data.model.Repo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class MainState(
    val reposPaging: Flow<PagingData<Repo>> = flowOf(
        PagingData.from(
            emptyList(),
            LoadStates(
                prepend = LoadState.NotLoading(endOfPaginationReached = true),
                append = LoadState.NotLoading(endOfPaginationReached = true),
                refresh = LoadState.NotLoading(endOfPaginationReached = true),
            )
        )
    ),
    val loading: Boolean = false,
    val error: String? = null
)
