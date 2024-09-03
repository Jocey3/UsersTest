package com.users.test.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AppRoutes(val route: String, val icon: ImageVector, val label: String) {
    data object AddUserRoute : AppRoutes("add-user", Icons.Default.Home, "Add user")
    data object ListUsersRoute : AppRoutes("list-users", Icons.Default.Person, "Users list")
}