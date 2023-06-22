package com.example.composelazylayoutsample.ui.graph

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.composelazylayoutsample.ui.graph.bottombar.BottomBar

@Composable
fun RootNavHost(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
        ) {
            SampleGraphs(navController = navController)
        }
    }
}
