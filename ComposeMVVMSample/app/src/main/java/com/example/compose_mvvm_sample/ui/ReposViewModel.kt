package com.example.compose_mvvm_sample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose_mvvm_sample.data.ResultWrapper
import com.example.compose_mvvm_sample.data.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReposViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReposUiState())
    val uiState: StateFlow<ReposUiState> = _uiState.asStateFlow()

    fun searchRepos(query: String) {
        viewModelScope.launch {
            githubRepository.searchRepos(query)
                .onStart { _uiState.update { it.copy(state = UiState.LOADING) } }
                .collect { searchResult ->
                    if (searchResult is ResultWrapper.Success) {
                        searchResult.data.let { result ->
                            _uiState.update {
                                it.copy(
                                    state = UiState.SUCCESS,
                                    repos = result.items
                                )
                            }
                        }
                    } else if (searchResult is ResultWrapper.Error) {
                        _uiState.update {
                            it.copy(
                                state = UiState.ERROR,
                                errorMessage = searchResult.errorMessage,
                                repos = listOf()
                            )
                        }
                    }
                }
        }
    }

}