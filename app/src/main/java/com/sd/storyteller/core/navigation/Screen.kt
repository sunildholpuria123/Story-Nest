package com.sd.storyteller.core.navigation

/**
 * Created by SDHOLPURIA on 31-07-2026.
 */

sealed class Screen(
    val route: String
) {

    data object Splash : Screen(
        "splash"
    )

    data object Home : Screen(
        "home"
    )

    data object Library : Screen(
        "library"
    )

    data object Favorites : Screen(
        "favorites"
    )

    data object Settings : Screen(
        "settings"
    )

    data object Create : Screen(
        "create"
    )

    data object Reader : Screen(
        route = "reader/{storyId}"
    ) {

        fun createRoute(
            storyId: Long
        ): String {
            return "reader/$storyId"
        }
    }

    data object Category : Screen(
        route = "category/{category}"
    ) {

        fun createRoute(
            category: String
        ): String {
            return "category/$category"
        }
    }

    data object Search : Screen(
        route = "search"
    )
}