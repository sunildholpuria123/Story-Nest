package com.sd.storyteller.core.navigation

/**
 * Created by SDHOLPURIA on 31-07-2026.
 */

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomBarDestination(
    val screen: Screen,
    val label: String,
    val icon: ImageVector
)

val BottomBarDestinations = listOf(
    BottomBarDestination(
        screen = Screen.Home,
        label = "Home",
        icon = Icons.Outlined.Home
    ),
    BottomBarDestination(
        screen = Screen.Library,
        label = "Library",
        icon = Icons.Outlined.LibraryBooks
    ),
    BottomBarDestination(
        screen = Screen.Favorites,
        label = "Favorites",
        icon = Icons.Outlined.FavoriteBorder
    ),
    BottomBarDestination(
        screen = Screen.Settings,
        label = "Settings",
        icon = Icons.Outlined.Settings
    )
)