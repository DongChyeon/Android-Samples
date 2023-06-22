package com.example.composelazylayoutsample.ui.graph.bottombar

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.composelazylayoutsample.ui.graph.Screen

@Composable
fun BottomBar(
    navController: NavHostController,
    bottomNavItems: Array<Screen> = Screen.values(),
) {
    BottomNavigation(
        backgroundColor = Color.White,
        elevation = 0.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        bottomNavItems.forEachIndexed { _, screen ->
            val isSelected =
                currentDestination?.hierarchy?.any { it.route == screen.route } == true
            BottomNavigationItem(
                icon = {
                    Surface {
                        Icon(
                            painter = painterResource(
                                id =
                                (if (isSelected) screen.selectedIcon else screen.unSelectedIcon),
                            ),
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                },
                label = null,
                selected = isSelected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(Screen.MAIN_GRAPH) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                selectedContentColor = Color.Unspecified,
                unselectedContentColor = Color.Unspecified,
            )
        }
    }
}
