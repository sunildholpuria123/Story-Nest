package com.sd.storyteller.core.navigation

/**
 * Created by SDHOLPURIA on 05-08-2026.
 */

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun StoryNestBottomBar(
    navController: NavHostController
) {

    val navBackStackEntry = navController.currentBackStackEntryAsState()

    val currentDestination = navBackStackEntry.value?.destination

    NavigationBar {

        BottomBarDestinations.forEach { destination ->

            val selected = currentDestination
                ?.hierarchy
                ?.any { navDestination ->
                    navDestination.route == destination.screen.route
                } == true

            NavigationBarItem(
                selected = selected,

                onClick = {

                    navController.navigate(destination.screen.route) {

                        popUpTo(
                            navController.graph.findStartDestination().id
                        ) {
                            saveState = true
                        }

                        launchSingleTop = true

                        restoreState = true
                    }
                },

                icon = {

                    Icon(
                        imageVector = destination.icon,
                        contentDescription = destination.label
                    )
                },

                label = {

                    Text(
                        text = destination.label
                    )
                }

            )
        }
    }
}