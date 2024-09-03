package com.users.test.presentation

import androidx.compose.animation.core.snap
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.users.test.presentation.screens.add_user.AddUserScreen
import com.users.test.presentation.util.AddUserRoute
import com.users.test.presentation.util.ListUsersRoute

@Composable
fun AppNavigationComponent(modifier: Modifier = Modifier) {
    val navController: NavHostController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = AddUserRoute,
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
        composable(AddUserRoute) { AddUserScreen() }
        composable(ListUsersRoute) {}
    }
}