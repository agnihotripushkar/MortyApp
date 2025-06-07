package com.devpush.morty.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavDestination(val title: String, val route: String, val icon: ImageVector) {
    object Home : NavDestination(title = "Home", route = "home_screen", icon = Icons.Filled.Home)
    object Episodes : NavDestination(title = "Episodes", route = "episodes", icon = Icons.Filled.PlayArrow)
    object Save : NavDestination(title = "Save", route = "save", icon = Icons.Filled.Star)
}