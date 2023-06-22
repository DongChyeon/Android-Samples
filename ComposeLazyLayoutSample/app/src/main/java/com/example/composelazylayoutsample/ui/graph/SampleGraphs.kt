package com.example.composelazylayoutsample.ui.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.composelazylayoutsample.ui.*
import com.example.composelazylayoutsample.ui.graph.Screen.Companion.MAIN_GRAPH

@Composable
fun SampleGraphs(navController: NavHostController) {
    NavHost(navController = navController, startDestination = MAIN_GRAPH) {
        mainNavigation()
    }
}

fun NavGraphBuilder.mainNavigation() {
    navigation(
        route = MAIN_GRAPH,
        startDestination = Screen.HOME.route
    ) {
        composable(Screen.HOME.route) {
            HomeScreen()
        }
        composable(Screen.NOTIFICATION.route) {
            NotificationScreen()
        }
        composable(Screen.CHAT.route) {
            ChatScreen()
        }
        composable(Screen.BOOKMARK.route) {
            BookmarkScreen()
        }
        composable(Screen.PROFILE.route) {
            ProfileScreen()
        }
    }
}