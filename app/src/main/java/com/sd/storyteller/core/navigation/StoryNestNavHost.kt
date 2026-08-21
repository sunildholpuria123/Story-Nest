package com.sd.storyteller.core.navigation

/**
 * Created by SDHOLPURIA on 31-07-2026.
 */

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sd.storyteller.feature.category.ui.CategoryScreen
import com.sd.storyteller.feature.create.ui.CreateStoryScreen
import com.sd.storyteller.feature.favorites.ui.FavoritesScreen
import com.sd.storyteller.feature.home.ui.HomeScreen
import com.sd.storyteller.feature.library.ui.LibraryScreen
import com.sd.storyteller.feature.reader.ui.ReaderScreen
import com.sd.storyteller.feature.search.SearchScreen
import com.sd.storyteller.feature.settings.ui.SettingsScreen
import com.sd.storyteller.feature.splash.ui.SplashScreen

@Composable
fun StoryNestNavHost() {

    val navController =
        rememberNavController()

    val currentRoute =
        navController
            .currentBackStackEntryAsState()
            .value
            ?.destination
            ?.route

    val showBottomBar =
        when (currentRoute) {

            Screen.Home.route,
            Screen.Library.route,
            Screen.Favorites.route,
            Screen.Settings.route -> true

            else -> false
        }

    Scaffold(

        // ---------------------------------------------------------
        // Important:
        // Do not apply system-bar insets at the root level.
        // Individual screens / TopAppBar handle their own insets.
        // ---------------------------------------------------------

        contentWindowInsets =
            WindowInsets(0, 0, 0, 0),

        bottomBar = {

            if (showBottomBar) {

                StoryNestBottomBar(
                    navController
                )
            }
        }

    ) { paddingValues ->

        NavHost(

            modifier =
                Modifier.padding(
                    paddingValues
                ),

            navController =
                navController,

            startDestination =
                Screen.Splash.route

        ) {

            // -----------------------------------------------------
            // Splash
            // -----------------------------------------------------

            composable(
                Screen.Splash.route
            ) {
                SplashScreen(
                    navController
                )
            }

            // -----------------------------------------------------
            // Home
            // -----------------------------------------------------

            composable(
                Screen.Home.route
            ) {
                HomeScreen(
                    navController
                )
            }

            // -----------------------------------------------------
            // Create Story
            // -----------------------------------------------------

            composable(
                Screen.Create.route
            ) {
                CreateStoryScreen(
                    navController
                )
            }

            // -----------------------------------------------------
            // Library
            // -----------------------------------------------------

            composable(
                Screen.Library.route
            ) {
                LibraryScreen(
                    navController
                )
            }

            // -----------------------------------------------------
            // Favorites
            // -----------------------------------------------------

            composable(
                Screen.Favorites.route
            ) {
                FavoritesScreen(
                    navController
                )
            }

            // -----------------------------------------------------
            // Settings
            // -----------------------------------------------------

            composable(
                Screen.Settings.route
            ) {
                SettingsScreen(
                    navController
                )
            }

            // -----------------------------------------------------
            // Reader
            // -----------------------------------------------------

            composable(
                route = Screen.Reader.route,

                arguments = listOf(
                    navArgument("storyId") {
                        type =
                            NavType.LongType
                    }
                )

            ) {

                ReaderScreen(
                    navController
                )
            }

            // -----------------------------------------------------
            // Category
            // -----------------------------------------------------

            composable(

                route =
                    Screen.Category.route,

                arguments = listOf(
                    navArgument("category") {
                        type =
                            NavType.StringType
                    }
                )

            ) {

                CategoryScreen(
                    navController =
                        navController
                )
            }

            // -----------------------------------------------------
            // Search
            // -----------------------------------------------------

            composable(
                route = Screen.Search.route
            ) {

                SearchScreen(
                    navController =
                        navController
                )
            }
        }
    }
}