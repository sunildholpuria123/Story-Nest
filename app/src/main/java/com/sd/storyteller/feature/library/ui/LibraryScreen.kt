package com.sd.storyteller.feature.library.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.sd.storyteller.core.designsystem.theme.StoryNestPalette
import com.sd.storyteller.core.navigation.Screen
import com.sd.storyteller.feature.library.component.LibraryEmptyState
import com.sd.storyteller.feature.library.component.LibraryStoryItem
import com.sd.storyteller.feature.library.component.LibraryTabRow
import com.sd.storyteller.feature.library.viewmodel.LibraryViewModel

/**
 * Created by SDHOLPURIA on 07-08-2026.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    navController: NavController,
    viewModel: LibraryViewModel =
        hiltViewModel()
) {

    val uiState by viewModel.uiState
        .collectAsStateWithLifecycle()

    var storyToDelete by remember {
        mutableStateOf<Long?>(null)
    }

    // ---------------------------------------------------------
    // Delete Confirmation Dialog
    // ---------------------------------------------------------

    val deleteStory =
        uiState.stories.firstOrNull {
            it.id == storyToDelete
        }

    if (deleteStory != null) {

        AlertDialog(

            onDismissRequest = {
                storyToDelete = null
            },

            title = {
                Text(
                    text = "Delete Story?"
                )
            },

            text = {
                Text(
                    text =
                        "Are you sure you want to delete " +
                                "\"${deleteStory.title}\"? " +
                                "This action cannot be undone."
                )
            },

            confirmButton = {

                Button(
                    onClick = {

                        viewModel.deleteStory(
                            deleteStory.id
                        )

                        storyToDelete = null
                    }
                ) {

                    Text(
                        text = "Delete"
                    )
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        storyToDelete = null
                    }
                ) {

                    Text(
                        text = "Cancel"
                    )
                }
            }
        )
    }

    // ---------------------------------------------------------
    // Screen
    // ---------------------------------------------------------

    Scaffold(

        // -----------------------------------------------------
        // Library Toolbar
        // -----------------------------------------------------

        topBar = {

            TopAppBar(

                title = {
                    Text(
                        text = "Library"
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

        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
        ) {

            when {

                // -------------------------------------------------
                // Loading
                // -------------------------------------------------

                uiState.isLoading -> {

                    CircularProgressIndicator(

                        modifier =
                            Modifier.align(
                                Alignment.Center
                            )
                    )
                }

                // -------------------------------------------------
                // Error
                // -------------------------------------------------

                uiState.error != null -> {

                    Text(

                        text =
                            uiState.error
                                ?: "Unable to load library.",

                        modifier =
                            Modifier.align(
                                Alignment.Center
                            ),

                        color =
                            StoryNestPalette.TextPrimary,

                        style =
                            MaterialTheme.typography.bodyLarge
                    )
                }

                // -------------------------------------------------
                // Content
                // -------------------------------------------------

                else -> {

                    Column(
                        modifier =
                            Modifier.fillMaxSize()
                    ) {

                        // -----------------------------------------
                        // Tabs
                        // -----------------------------------------

                        LibraryTabRow(

                            selectedTab =
                                uiState.selectedTab,

                            onTabSelected =
                                viewModel::selectTab,

                            modifier =
                                Modifier.padding(
                                    horizontal = 20.dp,
                                    vertical = 12.dp
                                )
                        )

                        // -----------------------------------------
                        // Story List
                        // -----------------------------------------

                        if (
                            uiState.visibleStories.isEmpty()
                        ) {

                            LibraryEmptyState(

                                tab =
                                    uiState.selectedTab,

                                modifier =
                                    Modifier
                                        .fillMaxSize()
                                        .padding(
                                            20.dp
                                        )
                            )

                        } else {

                            LazyColumn(

                                modifier =
                                    Modifier.fillMaxSize(),

                                contentPadding =
                                    PaddingValues(
                                        horizontal = 20.dp,
                                        vertical = 8.dp
                                    ),

                                verticalArrangement =
                                    Arrangement.spacedBy(
                                        12.dp
                                    )

                            ) {

                                items(

                                    items =
                                        uiState.visibleStories,

                                    key = {
                                        it.id
                                    }

                                ) { story ->

                                    LibraryStoryItem(

                                        story =
                                            story,

                                        onClick = {

                                            navController.navigate(
                                                Screen.Reader
                                                    .createRoute(
                                                        story.id
                                                    )
                                            )
                                        },

                                        onDelete = {

                                            storyToDelete =
                                                story.id
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}