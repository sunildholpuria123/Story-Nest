package com.sd.storyteller.feature.category.ui

/**
 * Created by SDHOLPURIA on 07-08-2026.
 */

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.sd.storyteller.core.designsystem.component.StoryCard
import com.sd.storyteller.core.navigation.Screen
import com.sd.storyteller.feature.reader.component.ReaderTopBar

@Composable
fun CategoryScreen(
    navController: NavController,
    viewModel: CategoryViewModel = hiltViewModel()
) {

    val uiState by
    viewModel.uiState
        .collectAsStateWithLifecycle()

    Scaffold(

        topBar = {

            ReaderTopBar(

                title = uiState.category,

                onBack = {
                    navController.popBackStack()
                },

                onShare = {}
            )
        }

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

                    Column(
                        modifier =
                            Modifier.align(
                                Alignment.Center
                            ),
                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {

                        Text(
                            text =
                                uiState.error
                                    ?: "Unable to load stories."
                        )
                    }
                }

                uiState.stories.isEmpty() -> {

                    Text(
                        text =
                            "No stories in this category yet.",
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

                            StoryCard (
                                story = story,
                                onClick = {

                                    navController.navigate(Screen.Reader.createRoute(story.id))

                                }
                            )
                        }
                    }
                }
            }
        }
    }
}