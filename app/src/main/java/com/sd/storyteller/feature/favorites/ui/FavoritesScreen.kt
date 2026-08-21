package com.sd.storyteller.feature.favorites.ui

/**
 * Created by SDHOLPURIA on 05-08-2026.
 */

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.sd.storyteller.core.designsystem.component.StoryCard
import com.sd.storyteller.core.designsystem.theme.StoryNestPalette
import com.sd.storyteller.core.navigation.Screen
import com.sd.storyteller.feature.favorites.viewmodel.FavoritesViewModel
import com.sd.storyteller.feature.reader.component.ReaderTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: FavoritesViewModel =
        hiltViewModel()
) {

    val uiState by
    viewModel.uiState
        .collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Favorites"
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            when {

                uiState.isLoading -> {

                    CircularProgressIndicator(
                        modifier =
                            Modifier.align(
                                Alignment.Center
                            )
                    )
                }

                uiState.error != null -> {

                    Text(
                        text =
                            uiState.error
                                ?: "Unable to load favorites.",
                        modifier =
                            Modifier.align(
                                Alignment.Center
                            )
                    )
                }

                uiState.stories.isEmpty() -> {

                    Text(
                        text =
                            "No favorite stories yet.",
                        modifier =
                            Modifier.align(
                                Alignment.Center
                            )
                    )
                }

                else -> {

                    LazyColumn(

                        modifier =
                            Modifier.fillMaxSize(),

                        contentPadding =
                            PaddingValues(20.dp),

                        verticalArrangement =
                            Arrangement.spacedBy(12.dp)

                    ) {

                        items(
                            items = uiState.stories,
                            key = { it.id }
                        ) { story ->

                            StoryCard(
                                story = story,
                                onClick = {

                                    navController.navigate(
                                        Screen.Reader
                                            .createRoute(
                                                story.id
                                            )
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}