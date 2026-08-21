package com.sd.storyteller.feature.home.ui

/**
 * Created by SDHOLPURIA on 31-07-2026.
 */

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.sd.storyteller.core.designsystem.dimention.StoryNestDimens
import com.sd.storyteller.core.designsystem.theme.StoryNestPalette
import com.sd.storyteller.core.navigation.Screen
import com.sd.storyteller.feature.home.component.CategorySection
import com.sd.storyteller.feature.home.component.CreateStoryCard
import com.sd.storyteller.feature.home.component.HomeSearchBar
import com.sd.storyteller.feature.home.component.RecentStoriesSection
import com.sd.storyteller.feature.home.component.WelcomeSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState
        .collectAsStateWithLifecycle()

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text = "StoryNest"
                    )
                },

                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor =
                            StoryNestPalette.Surface,

                        titleContentColor =
                            StoryNestPalette.TextPrimary
                    )
            )
        },

        containerColor =
            StoryNestPalette.Background

    ) { paddingValues ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(
                    StoryNestDimens.Space16
                )
                .verticalScroll(
                    rememberScrollState()
                ),

            verticalArrangement =
                Arrangement.Top

        ) {

            // -------------------------------------------------
            // Search
            // -------------------------------------------------

            HomeSearchBar(

                onClick = {

                    navController.navigate(
                        Screen.Search.route
                    )
                }
            )

            Spacer(
                modifier = Modifier.height(
                    StoryNestDimens.Space24
                )
            )

            // -------------------------------------------------
            // Welcome
            // -------------------------------------------------

            WelcomeSection()

            Spacer(
                modifier = Modifier.height(
                    StoryNestDimens.Space24
                )
            )

            // -------------------------------------------------
            // Create Story
            // -------------------------------------------------

            CreateStoryCard(

                onCreateStory = {

                    navController.navigate(
                        Screen.Create.route
                    )
                }
            )

            Spacer(
                modifier = Modifier.height(
                    StoryNestDimens.Space32
                )
            )

            // -------------------------------------------------
            // Categories
            // -------------------------------------------------

            CategorySection(

                onCategoryClick = { category ->

                    navController.navigate(
                        Screen.Category
                            .createRoute(category)
                    )
                }
            )

            Spacer(
                modifier = Modifier.height(
                    StoryNestDimens.Space32
                )
            )

            // -------------------------------------------------
            // Recent Stories
            // -------------------------------------------------

            RecentStoriesSection(

                stories =
                    uiState.recentStories,

                onStoryClick = { storyId ->

                    navController.navigate(
                        Screen.Reader
                            .createRoute(storyId)
                    )
                }
            )
        }
    }
}