package com.hana.composemvisample.ui

import com.hana.composemvisample.data.model.Repo

data class MainState(
    val repos: List<Repo> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null
)
