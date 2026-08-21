package com.sd.storyteller.feature.reader.ui

/**
 * Created by SDHOLPURIA on 01-08-2026.
 */

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.sd.storyteller.core.designsystem.dimention.StoryNestDimens
import com.sd.storyteller.core.designsystem.theme.StoryNestPalette
import com.sd.storyteller.feature.reader.background.ThemeBackground
import com.sd.storyteller.feature.reader.book.StoryBook
import com.sd.storyteller.feature.reader.component.ReaderBottomBar
import com.sd.storyteller.feature.reader.component.ReaderErrorState
import com.sd.storyteller.feature.reader.component.ReaderLoadingState
import com.sd.storyteller.feature.reader.component.ReaderTopBar
import com.sd.storyteller.feature.reader.component.ReadingProgress
import com.sd.storyteller.feature.reader.component.StoryContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderScreen(
    navController: NavController,
    viewModel: ReaderViewModel = hiltViewModel()
) {

    val uiState by
    viewModel.uiState
        .collectAsStateWithLifecycle()

    val context = LocalContext.current

    // ---------------------------------------------------------
    // Navigation
    // ---------------------------------------------------------

    val navigateBack = {

        viewModel.onBack()

        navController.popBackStack()
    }

    BackHandler {
        navigateBack()
    }

    // ---------------------------------------------------------
    // Share Story
    // ---------------------------------------------------------

    val shareStory = {

        if (
            uiState.content.isNotBlank()
        ) {

            val shareText =
                buildString {

                    if (
                        uiState.title.isNotBlank()
                    ) {

                        append(
                            uiState.title
                        )

                        append(
                            "\n\n"
                        )
                    }

                    append(
                        uiState.content
                    )

                    append(
                        "\n\n"
                    )

                    append(
                        "Created with StoryNest ✨"
                    )
                }

            val shareIntent =
                Intent(
                    Intent.ACTION_SEND
                ).apply {

                    type =
                        "text/plain"

                    putExtra(
                        Intent.EXTRA_SUBJECT,
                        uiState.title.ifBlank {
                            "StoryNest Story"
                        }
                    )

                    putExtra(
                        Intent.EXTRA_TEXT,
                        shareText
                    )
                }

            context.startActivity(
                Intent.createChooser(
                    shareIntent,
                    "Share Story"
                )
            )
        }
    }

    // ---------------------------------------------------------
    // Screen
    // ---------------------------------------------------------

    Box(
        modifier =
            Modifier.fillMaxSize()
    ) {

        // -----------------------------------------------------
        // Theme Background
        // -----------------------------------------------------

        ThemeBackground(
            theme =
                uiState.theme
        )

        // -----------------------------------------------------
        // Scaffold
        // -----------------------------------------------------

        Scaffold(

            modifier =
                Modifier.fillMaxSize(),

            containerColor =
                StoryNestPalette.Transparent,

            // -------------------------------------------------
            // Top Bar
            // -------------------------------------------------

            topBar = {

                ReaderTopBar(

                    title =
                        if (
                            uiState.title.isBlank()
                        ) {
                            "Story"
                        } else {
                            uiState.title
                        },

                    onBack = {
                        navigateBack()
                    },

                    onShare = {
                        shareStory()
                    }
                )
            },

            // -------------------------------------------------
            // Bottom Bar
            // -------------------------------------------------

            bottomBar = {

                if (
                    !uiState.isLoading &&
                    uiState.error == null
                ) {

                    ReaderBottomBar(

                        isFavorite =
                            uiState.isFavorite,

                        musicEnabled =
                            uiState.musicEnabled,

                        isSpeaking =
                            uiState.isSpeaking,

                        isPaused =
                            uiState.isPaused,

                        ttsReady =
                            uiState.ttsReady,

                        onFavorite =
                            viewModel::toggleFavorite,

                        onPlay =
                            viewModel::play,

                        onPause =
                            viewModel::pause,

                        onStop =
                            viewModel::stop,

                        onMusic ={}
                    )
                }
            }

        ) { paddingValues ->

            Box(

                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(
                            paddingValues
                        )
                        .padding(
                            StoryNestDimens.Space20
                        )
            ) {

                when {

                    // =========================================
                    // Loading
                    // =========================================

                    uiState.isLoading -> {

                        ReaderLoadingState(
                            modifier =
                                Modifier.fillMaxSize()
                        )
                    }

                    // =========================================
                    // Error
                    // =========================================

                    uiState.error != null -> {

                        ReaderErrorState(

                            message =
                                uiState.error
                                    ?: "Unable to load story.",

                            onRetry =
                                viewModel::retry,

                            onBack = {
                                navigateBack()
                            },

                            modifier =
                                Modifier.fillMaxSize()
                        )
                    }

                    // =========================================
                    // Story
                    // =========================================

                    else -> {

                        Column(

                            modifier =
                                Modifier.fillMaxSize()
                        ) {

                            // ---------------------------------
                            // TTS Loading
                            // ---------------------------------

                            if (
                                !uiState.ttsReady
                            ) {

                                Text(

                                    text =
                                        "Preparing audio…",

                                    style =
                                        MaterialTheme
                                            .typography
                                            .labelMedium,

                                    color =
                                        StoryNestPalette
                                            .TextSecondary
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(
                                            8.dp
                                        )
                                )
                            }

                            // ---------------------------------
                            // Reading Progress
                            // ---------------------------------

                            ReadingProgress(

                                currentSentence =
                                    uiState.currentSentence,

                                totalSentences =
                                    uiState.totalSentences
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(
                                        12.dp
                                    )
                            )

                            // ---------------------------------
                            // Story Book
                            // ---------------------------------

                            StoryBook(

                                title =
                                    uiState.title

                            ) {

                                StoryContent(

                                    content =
                                        uiState.content,

                                    currentSentence =
                                        uiState.currentSentence,

                                    isSpeaking =
                                        uiState.isSpeaking,

                                    language =
                                        uiState.language
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}