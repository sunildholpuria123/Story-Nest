package com.sd.storyteller.feature.create.ui

/**
 * Created by SDHOLPURIA on 01-08-2026.
 */

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.sd.storyteller.core.constants.StoryCategories
import com.sd.storyteller.core.constants.StoryLanguage
import com.sd.storyteller.core.designsystem.component.StoryCategoryDropdown
import com.sd.storyteller.core.designsystem.component.StoryDropdown
import com.sd.storyteller.core.designsystem.dimention.StoryNestDimens
import com.sd.storyteller.core.designsystem.theme.StoryNestPalette
import com.sd.storyteller.core.navigation.Screen
import com.sd.storyteller.feature.create.component.StoryTopicSelector
import com.sd.storyteller.feature.create.event.CreateStoryEvent
import com.sd.storyteller.feature.create.model.StoryCategory
import com.sd.storyteller.feature.create.model.StoryLength
import com.sd.storyteller.feature.create.model.StoryMood
import com.sd.storyteller.feature.create.viewmodel.CreateStoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateStoryScreen(
    navController: NavController,
    viewModel: CreateStoryViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState
        .collectAsStateWithLifecycle()

    val snackbarHostState =
        remember {
            SnackbarHostState()
        }

    // ---------------------------------------------------------
    // Generated Story
    // ---------------------------------------------------------

    LaunchedEffect(Unit) {

        viewModel.story.collect { story ->

            navController.navigate(
                Screen.Reader.createRoute(
                    story.id
                )
            )
        }
    }

    // ---------------------------------------------------------
    // Error
    // ---------------------------------------------------------

    LaunchedEffect(uiState.error) {

        uiState.error?.let { message ->

            snackbarHostState.showSnackbar(
                message = message
            )

            viewModel.clearError()
        }
    }

    // ---------------------------------------------------------
    // Scaffold
    // ---------------------------------------------------------

    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text(
                        text = "Create Story"
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

        snackbarHost = {

            SnackbarHost(
                hostState =
                    snackbarHostState
            )
        },

        containerColor =
            StoryNestPalette.Background

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
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
            // Header
            // -------------------------------------------------

            Text(
                text = "Create Your Story",
                style =
                    MaterialTheme.typography
                        .headlineMedium,
                color =
                    StoryNestPalette.TextPrimary
            )

            Spacer(
                modifier = Modifier.height(
                    StoryNestDimens.Space24
                )
            )

            // -------------------------------------------------
            // Character
            // -------------------------------------------------

            OutlinedTextField(

                modifier =
                    Modifier.fillMaxWidth(),

                value =
                    uiState.characterName,

                onValueChange = {

                    viewModel.onEvent(
                        CreateStoryEvent
                            .CharacterChanged(it)
                    )
                },

                label = {
                    Text("Character Name")
                },

                singleLine = true
            )

            Spacer(
                modifier = Modifier.height(
                    StoryNestDimens.Space16
                )
            )

            // -------------------------------------------------
            // Age
            // -------------------------------------------------

            OutlinedTextField(

                modifier =
                    Modifier.fillMaxWidth(),

                value =
                    uiState.age,

                onValueChange = {

                    viewModel.onEvent(
                        CreateStoryEvent
                            .AgeChanged(it)
                    )
                },

                label = {
                    Text("Age")
                },

                singleLine = true,

                keyboardOptions =
                    KeyboardOptions(
                        keyboardType =
                            KeyboardType.Number
                    )
            )

            Spacer(
                modifier = Modifier.height(
                    StoryNestDimens.Space24
                )
            )

            // -------------------------------------------------
            // Category
            // -------------------------------------------------

            StoryCategoryDropdown(

                label = "Story Category",

                value =
                    "${uiState.category.emoji} " +
                            uiState.category.name,

                items =
                    StoryCategories.all,

                onSelected = { selected ->

                    viewModel.onEvent(

                        CreateStoryEvent
                            .CategoryChanged(

                                StoryCategory(
                                    id = selected.id,
                                    name = selected.name,
                                    description =
                                        selected.description,
                                    emoji =
                                        selected.emoji
                                )
                            )
                    )
                }
            )

            Spacer(
                modifier = Modifier.height(
                    StoryNestDimens.Space16
                )
            )

            // -------------------------------------------------
            // Topic
            // -------------------------------------------------

            StoryTopicSelector(

                selectedTopic =
                    uiState.topic,

                onTopicSelected = {

                    viewModel.onEvent(
                        CreateStoryEvent
                            .TopicChanged(it)
                    )
                }
            )

            Spacer(
                modifier = Modifier.height(
                    StoryNestDimens.Space16
                )
            )

            // -------------------------------------------------
            // Story Length
            // -------------------------------------------------

            StoryDropdown(

                label = "Story Length",

                value =
                    uiState.length.title,

                items =
                    StoryLength.entries.map {
                        it.title
                    },

                onSelected = { selected ->

                    viewModel.onEvent(

                        CreateStoryEvent
                            .LengthChanged(

                                StoryLength.entries
                                    .first {
                                        it.title ==
                                                selected
                                    }
                            )
                    )
                }
            )

            Spacer(
                modifier = Modifier.height(
                    StoryNestDimens.Space16
                )
            )

            // -------------------------------------------------
            // Story Mood
            // -------------------------------------------------

            StoryDropdown(

                label = "Story Mood",

                value =
                    uiState.mood.title,

                items =
                    StoryMood.entries.map {
                        it.title
                    },

                onSelected = { selected ->

                    viewModel.onEvent(

                        CreateStoryEvent
                            .MoodChanged(

                                StoryMood.entries
                                    .first {
                                        it.title ==
                                                selected
                                    }
                            )
                    )
                }
            )

            Spacer(
                modifier = Modifier.height(
                    StoryNestDimens.Space16
                )
            )

            // -------------------------------------------------
            // Story Language
            // -------------------------------------------------

//            StoryDropdown(
//
//                label = "Story Language",
//
//                value =
//                    "${uiState.language.emoji} " +
//                            uiState.language.displayName,
//
//                items =
//                    StoryLanguage.entries.map {
//
//                        "${it.emoji} " +
//                                it.displayName
//                    },
//
//                onSelected = { selected ->
//
//                    val language =
//                        StoryLanguage.entries
//                            .first {
//
//                                "${it.emoji} " +
//                                        it.displayName ==
//                                        selected
//                            }
//
//                    viewModel.onEvent(
//
//                        CreateStoryEvent
//                            .StoryLanguageChanged(
//                                language
//                            )
//                    )
//                }
//            )
//
//            Spacer(
//                modifier = Modifier.height(
//                    StoryNestDimens.Space32
//                )
//            )

            // -------------------------------------------------
            // Daily Attempts
            // -------------------------------------------------

            Text(
                text = buildString {

                    append(
                        uiState.remainingAttempts
                    )

                    append(
                        if (
                            uiState.remainingAttempts == 1
                        ) {
                            " story attempt"
                        } else {
                            " story attempts"
                        }
                    )

                    append(
                        " remaining today"
                    )
                },

                color =
                    StoryNestPalette.TextSecondary,

                style =
                    MaterialTheme.typography.bodyMedium
            )

            Spacer(
                modifier = Modifier.height(
                    StoryNestDimens.Space8
                )
            )

            // -------------------------------------------------
            // Generate
            // -------------------------------------------------

            Button(

                modifier =
                    Modifier.fillMaxWidth(),

                enabled =
                    !uiState.isLoading &&
                            uiState.remainingAttempts > 0,

                onClick = {

                    viewModel.onEvent(
                        CreateStoryEvent
                            .GenerateStory
                    )
                }

            ) {

                if (uiState.isLoading) {

                    CircularProgressIndicator(

                        modifier =
                            Modifier.height(
                                20.dp
                            ),

                        strokeWidth = 2.dp
                    )

                } else {

                    Text(
                        text =
                            if (
                                uiState.remainingAttempts > 0
                            ) {
                                "Generate Story ✨"
                            } else {
                                "Daily Limit Reached"
                            }
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(
                    StoryNestDimens.Space16
                )
            )
        }
    }
}