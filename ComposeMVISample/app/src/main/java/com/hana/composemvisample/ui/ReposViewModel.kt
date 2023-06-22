package com.hana.composemvisample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.hana.composemvisample.data.datasource.RepoPagingSource
import com.hana.composemvisample.data.datasource.RepoPagingSource.Companion.PAGE_SIZE
import com.hana.composemvisample.data.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReposViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {

    private val events = Channel<MainEvent>()

    val state: StateFlow<MainState> = events.receiveAsFlow()
        .runningFold(MainState(), ::reduceState)
        .stateIn(viewModelScope, SharingStarted.Eagerly, MainState())

    private fun reduceState(current: MainState, event: MainEvent): MainState {
        return when (event) {
            is MainEvent.Loading -> {
                current.copy(loading = true, error = null)
            }
            is MainEvent.ShowError -> {
                current.copy(loading = false, error = event.error)
            }
            is MainEvent.ShowReposPaging -> {
                current.copy(loading = false, reposPaging = event.repos, error = null)
            }
        }
    }

    fun searchReposPaging(query: String) = viewModelScope.launch {
        events.send(
            MainEvent.ShowReposPaging(
                repos = Pager(
                    config = PagingConfig(pageSize = PAGE_SIZE),
                    pagingSourceFactory = {
                        RepoPagingSource(
                            githubRepository = githubRepository,
                            query = query,
                        )
                    },
                ).flow.cachedIn(viewModelScope)
            )
        )
    }

    // 기존 리스트로 가져오는 소스
//    fun searchRepos(query: String) {
//        viewModelScope.launch {
//            githubRepository.searchRepos(query)
//                .onStart { events.send(MainEvent.Loading) }
//                .collect { searchResult ->
//                    searchResult.isSuccess { result ->
//                        events.send(MainEvent.ShowRepos(repos = result.items))
//                    }
//                    searchResult.isError { errorMessage ->
//                        events.send(MainEvent.ShowError(error = errorMessage))
//                    }
//                }
//        }
//    }
}