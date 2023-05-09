package com.hana.composemvisample.ui

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
                val state by viewModel.state.collectAsStateWithLifecycle()

                SearchScreen(state = state, searchBtnOnClick = viewModel::searchRepos)
            }
        }
    }
}