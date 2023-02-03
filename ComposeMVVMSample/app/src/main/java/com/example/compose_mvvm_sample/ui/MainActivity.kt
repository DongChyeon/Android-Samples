package com.example.compose_mvvm_sample.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose_mvvm_sample.ui.theme.ComposeMVVMSampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMVVMSampleTheme {
                val viewModel: ReposViewModel = viewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                SearchScreen(uiState = uiState, searchBtnOnClick = viewModel::searchRepos)
            }
        }
    }
}