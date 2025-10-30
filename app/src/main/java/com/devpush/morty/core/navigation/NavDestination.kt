package com.devpush.morty.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.devpush.morty.core.constants.UiConstants

sealed class NavDestination(val title: String, val route: String, val icon: ImageVector) {
    object Home : NavDestination(title = UiConstants.NAV_DESTINATION_HOME, route = "home_screen", icon = Icons.Filled.Home)
    object Episodes : NavDestination(title = UiConstants.NAV_DESTINATION_EPISODES, route = "episodes", icon = Icons.Filled.PlayArrow)
//    object Save : NavDestination(title = "Save", route = "save", icon = Icons.Filled.Star)
}