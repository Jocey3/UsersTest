package com.users.test.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.users.test.presentation.util.Args.USER_ID

sealed class AppRoutes(val route: String, val icon: ImageVector, val label: String) {
    data object AddUserRoute : AppRoutes("add-user/{$USER_ID}", Icons.Default.Home, "Add user")
    data object ListUsersRoute : AppRoutes("list-users", Icons.Default.Person, "Users list")
}

object Args {
    const val USER_ID = "user_id"
}
