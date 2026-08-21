package com.sd.storyteller.feature.search

/**
 * Created by SDHOLPURIA on 07-08-2026.
 */

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.sd.storyteller.core.designsystem.component.StoryCard
import com.sd.storyteller.core.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {

    val uiState by
    viewModel.uiState
        .collectAsStateWithLifecycle()

    Scaffold(

        topBar = {

            TopAppBar(
                title = {
                    Text("Search Stories")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp)
        ) {

            OutlinedTextField(

                value = uiState.query,

                onValueChange =
                    viewModel::updateQuery,

                modifier =
                    Modifier.fillMaxWidth(),

                singleLine = true,

                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null
                    )
                },

                trailingIcon = {

                    if (uiState.query.isNotBlank()) {

                        IconButton(
                            onClick =
                                viewModel::clearSearch
                        ) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription =
                                    "Clear"
                            )
                        }
                    }
                },

                placeholder = {
                    Text(
                        "Search stories, characters..."
                    )
                }
            )

            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )

            if (
                uiState.query.isBlank()
            ) {

                Text(
                    text = "Search for a story",
                    style =
                        MaterialTheme.typography
                            .titleMedium
                )

            } else if (
                uiState.stories.isEmpty()
            ) {

                Text(
                    text = "No stories found.",
                    style =
                        MaterialTheme.typography
                            .bodyLarge
                )

            } else {

                LazyColumn(
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