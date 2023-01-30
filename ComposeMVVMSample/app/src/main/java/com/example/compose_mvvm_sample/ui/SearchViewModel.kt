package com.example.compose_mvvm_sample.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose_mvvm_sample.data.Result
import com.example.compose_mvvm_sample.data.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    fun searchRepos(query: String) {
        Log.d("검색", "실행")
        viewModelScope.launch {
            githubRepository.searchRepos(query)
                .onStart { _uiState.update { it.copy(state = UiState.LOADING) } }
                .collectLatest { searchResult ->
                    if (searchResult is Result.Success) {
                        searchResult.data.let { result ->
                            _uiState.update {
                                it.copy(
                                    state = UiState.SUCCESS,
                                    repos = result.items
                                )
                            }
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                state = UiState.ERROR,
                                repos = listOf()
                            )
                        }
                    }
                }
        }
    }

}