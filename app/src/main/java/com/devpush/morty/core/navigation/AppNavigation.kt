package com.devpush.morty.core.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.devpush.morty.features.allepisodes.ui.AllEpisodesScreen
import com.devpush.morty.features.characterdetails.ui.CharacterDetailsScreen
import com.devpush.morty.features.episode.ui.CharacterEpisodeScreen
import com.devpush.morty.features.allcharacters.ui.HomeScreen
import com.devpush.morty.features.bookmark.ui.SaveScreen
import com.devpush.morty.core.navigation.NavDestination
import com.devpush.morty.ui.theme.RickAction
import com.devpush.morty.ui.theme.RickPrimary
import com.devpush.network.KtorClient // Assuming KtorClient is needed by screens


@Composable
fun AppNavigation(ktorClient: KtorClient) {
    val navController = rememberNavController()
    val items = listOf(
        NavDestination.Home, NavDestination.Episodes, NavDestination.Save
    )
    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            AppBottomNavigationBar(
                items = items,
                selectedIndex = selectedIndex,
                onItemSelected = { index, route ->
                    selectedIndex = index
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
fun AppBottomNavigationBar(
    items: List<NavDestination>,
    selectedIndex: Int,
    onItemSelected: (index: Int, route: String) -> Unit
) {
    NavigationBar(
        containerColor = RickPrimary
    ) {
        items.forEachIndexed { index, screen ->
            NavigationBarItem(
                icon = {
                    Icon(imageVector = screen.icon, contentDescription = screen.title)
                },
                label = { Text(screen.title) },
                selected = index == selectedIndex,
                onClick = { onItemSelected(index, screen.route) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = RickAction,
                    selectedTextColor = RickAction,
                    indicatorColor = Color.Transparent // Consider making this theme-dependent
                )
            )
        }
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

        composable(route = NavDestination.Save.route) {
            // Column might not be needed if SaveScreen handles its own layout
            SaveScreen()
        }
    }
}