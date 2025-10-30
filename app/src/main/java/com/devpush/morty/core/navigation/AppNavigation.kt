package com.devpush.morty.core.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.devpush.morty.R
import com.devpush.morty.core.commonui.M3ExpressiveBottomToolbar
import com.devpush.morty.features.allepisodes.ui.AllEpisodesScreen
import com.devpush.morty.features.characterdetails.ui.CharacterDetailsScreen
import com.devpush.morty.features.episode.ui.CharacterEpisodeScreen
import com.devpush.morty.features.allcharacters.ui.HomeScreen
import com.devpush.morty.core.commonui.M3ExpressiveTopToolbar
import com.devpush.morty.ui.theme.RickPrimary
import com.devpush.network.KtorClient // Assuming KtorClient is needed by screens

/**
 * Data class representing the current navigation state
 */
data class NavigationState(
    val currentRoute: String,
    val title: String,
    val showBackButton: Boolean = false
)

/**
 * Determines the navigation state based on the current route and back stack entry
 */
@Composable
private fun getNavigationState(
    currentRoute: String,
    navBackStackEntry: NavBackStackEntry?
): NavigationState {
    return when {
        currentRoute == NavDestination.Home.route -> NavigationState(
            currentRoute = currentRoute,
            title = stringResource(R.string.nav_title_home)
        )
        currentRoute == NavDestination.Episodes.route -> NavigationState(
            currentRoute = currentRoute,
            title = stringResource(R.string.nav_title_episodes)
        )
        currentRoute.contains("character_details") -> NavigationState(
            currentRoute = currentRoute,
            title = stringResource(R.string.nav_title_character_details),
            showBackButton = true
        )
        currentRoute.contains("character_episodes") -> NavigationState(
            currentRoute = currentRoute,
            title = stringResource(R.string.nav_title_character_episodes),
            showBackButton = true
        )
        else -> NavigationState(
            currentRoute = currentRoute,
            title = stringResource(R.string.app_title_default)
        )
    }
}

@Composable
fun AppNavigation(ktorClient: KtorClient) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: NavDestination.Home.route
    
    // Create navigation state with title mapping
    val navigationState = getNavigationState(currentRoute, navBackStackEntry)

    Scaffold(
        topBar = {
            M3ExpressiveTopToolbar(
                title = navigationState.title,
                showBackButton = navigationState.showBackButton,
                onBackClick = if (navigationState.showBackButton) {
                    { navController.navigateUp() }
                } else null,
            )
        },
        bottomBar = {
            M3ExpressiveBottomToolbar(
                currentRoute = currentRoute,
                onNavigationClick = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        AppNavigationHost(
            navController = navController,
            ktorClient = ktorClient,
            innerPadding = innerPadding
        )
    }
}



@Composable
fun AppNavigationHost(
    navController: NavHostController,
    ktorClient: KtorClient, // Accept KtorClient
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = NavDestination.Home.route, // Use route from sealed class
        modifier = Modifier
            .fillMaxSize() // Fill the available space given by Scaffold
            .background(color = RickPrimary)
            .padding(innerPadding) // Apply padding from Scaffold
    ) {
        composable(route = NavDestination.Home.route) {
            HomeScreen(
                onCharacterSelected = { characterId ->
                    navController.navigate("character_details/$characterId")
                }
            )
        }

        composable(
            route = "character_details/{characterId}",
            arguments = listOf(navArgument("characterId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val characterId: Int = backStackEntry.arguments?.getInt("characterId") ?: -1
            CharacterDetailsScreen(
                characterId = characterId,
                onEpisodeClicked = { episodeId -> navController.navigate("character_episodes/$episodeId") },
                onBackClicked = { navController.navigateUp() }
            )
        }

        composable(
            route = "character_episodes/{characterId}", // Assuming characterId is the right param
            arguments = listOf(navArgument("characterId") { // Keep if needed for episodes by character
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val characterId: Int = backStackEntry.arguments?.getInt("characterId") ?: -1
            CharacterEpisodeScreen(
                characterId = characterId,
                ktorClient = ktorClient, // Pass KtorClient here
                onBackClicked = { navController.navigateUp() }
            )
        }

        composable(route = NavDestination.Episodes.route) {
            // Column might not be needed if AllEpisodesScreen handles its own layout
            AllEpisodesScreen()
        }
    }
}