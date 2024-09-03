package com.users.test.presentation

import androidx.compose.animation.core.snap
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.users.test.presentation.screens.add_user.AddUserScreen
import com.users.test.presentation.screens.list_users.UserListScreen
import com.users.test.presentation.util.AppRoutes


@Composable
fun AppNavigationComponent(modifier: Modifier = Modifier) {
    val navController: NavHostController = rememberNavController()

    val items = listOf(AppRoutes.AddUserRoute, AppRoutes.ListUsersRoute)

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            modifier = modifier.padding(innerPadding),
            navController = navController,
            startDestination = AppRoutes.AddUserRoute.route,
            enterTransition = {
                fadeIn(initialAlpha = 0f, animationSpec = snap())
            },
            exitTransition = {
                fadeOut(targetAlpha = 1f, animationSpec = snap())
            },
            popEnterTransition = {
                fadeIn(initialAlpha = 0f, animationSpec = snap())
            },
            popExitTransition = {
                fadeOut(targetAlpha = 1f, animationSpec = snap())
            }
        ) {
            composable(AppRoutes.AddUserRoute.route) { AddUserScreen() }
            composable(AppRoutes.ListUsersRoute.route) { UserListScreen() }
        }
    }
}