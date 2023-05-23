package com.hana.composemvisample.ui

import androidx.paging.PagingData
import com.hana.composemvisample.data.model.Repo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class MainState(
    val reposPaging: Flow<PagingData<Repo>> = flowOf(PagingData.empty()),
    val loading: Boolean = false,
    val error: String? = null
)
