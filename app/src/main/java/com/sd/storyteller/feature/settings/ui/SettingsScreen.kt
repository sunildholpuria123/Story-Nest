package com.sd.storyteller.feature.settings.ui

/**
 * Created by SDHOLPURIA on 08-08-2026.
 */

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import com.sd.storyteller.core.constants.StoryLanguage
import com.sd.storyteller.core.designsystem.component.StoryDropdown
import com.sd.storyteller.core.designsystem.dimention.StoryNestDimens
import com.sd.storyteller.core.designsystem.theme.StoryNestPalette
import com.sd.storyteller.feature.settings.component.SettingsSwitchRow
import com.sd.storyteller.feature.settings.event.SettingsEvent
import com.sd.storyteller.feature.settings.viewmodel.SettingsViewModel
import com.sd.storyteller.ui.theme.StoryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel =
        hiltViewModel()
) {

    val uiState by
    viewModel.uiState
        .collectAsStateWithLifecycle()

    val navigateBack = {
        navController.popBackStack()
    }

    BackHandler {
        navigateBack()
    }

    Scaffold(
        containerColor =
            StoryNestPalette.Background

    ) { paddingValues ->

        when {

            // -------------------------------------------------
            // Loading
            // -------------------------------------------------

            uiState.isLoading -> {

                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(
                                paddingValues
                            ),
                    horizontalAlignment =
                        Alignment.CenterHorizontally,
                    verticalArrangement =
                        Arrangement.Center
                ) {

                    CircularProgressIndicator()
                }
            }

            // -------------------------------------------------
            // Error
            // -------------------------------------------------

            uiState.error != null -> {

                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(
                                paddingValues
                            )
                            .padding(
                                StoryNestDimens.Space24
                            ),
                    horizontalAlignment =
                        Alignment.CenterHorizontally,
                    verticalArrangement =
                        Arrangement.Center
                ) {

                    Text(
                        text =
                            uiState.error
                                ?: "Unable to load settings.",
                        color =
                            StoryNestPalette.TextPrimary
                    )

                    Spacer(
                        modifier =
                            Modifier.height(
                                StoryNestDimens.Space16
                            )
                    )

                    Button(
                        onClick = {
                            viewModel.onEvent(
                                SettingsEvent.Retry
                            )
                        }
                    ) {

                        Text(
                            text = "Retry"
                        )
                    }
                }
            }

            // -------------------------------------------------
            // Content
            // -------------------------------------------------

            else -> {

                Column(

                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(
                                paddingValues
                            )
                            .padding(
                                StoryNestDimens.Space16
                            )
                            .verticalScroll(
                                rememberScrollState()
                            ),

                    verticalArrangement =
                        Arrangement.Top

                ) {

                    // =================================================
                    // Story Preferences
                    // =================================================

                    Text(
                        text = "Story Preferences",
                        style =
                            MaterialTheme.typography
                                .titleLarge,
                        color =
                            StoryNestPalette.TextPrimary
                    )

                    Spacer(
                        modifier =
                            Modifier.height(
                                StoryNestDimens.Space8
                            )
                    )

                    Text(
                        text =
                            "Choose how your stories are created.",
                        style =
                            MaterialTheme.typography
                                .bodyMedium,
                        color =
                            StoryNestPalette.TextSecondary
                    )

                    Spacer(
                        modifier =
                            Modifier.height(
                                StoryNestDimens.Space16
                            )
                    )

                    // -------------------------------------------------
                    // Language
                    // -------------------------------------------------

                    StoryDropdown(

                        label = "Story Language",

                        value =
                            "${uiState.language.emoji} " +
                                    uiState.language.displayName,

                        items =
                            StoryLanguage.entries.map {
                                "${it.emoji} ${it.displayName}"
                            },

                        onSelected = { selected ->

                            val language =
                                StoryLanguage.entries
                                    .firstOrNull {
                                        "${it.emoji} " +
                                                it.displayName ==
                                                selected
                                    }

                            if (language != null) {

                                viewModel.onEvent(
                                    SettingsEvent
                                        .LanguageChanged(
                                            language
                                        )
                                )
                            }
                        },

                        modifier =
                            Modifier.fillMaxWidth()
                    )

                    Spacer(
                        modifier =
                            Modifier.height(
                                StoryNestDimens.Space32
                            )
                    )

                    // =================================================
                    // Reading
                    // =================================================

                    Text(
                        text = "Reading",
                        style =
                            MaterialTheme.typography
                                .titleLarge,
                        color =
                            StoryNestPalette.TextPrimary
                    )

                    Spacer(
                        modifier =
                            Modifier.height(
                                StoryNestDimens.Space8
                            )
                    )

                    Text(
                        text =
                            "Control narration and background music.",
                        style =
                            MaterialTheme.typography
                                .bodyMedium,
                        color =
                            StoryNestPalette.TextSecondary
                    )

                    Spacer(
                        modifier =
                            Modifier.height(
                                StoryNestDimens.Space8
                            )
                    )


                    Spacer(
                        modifier =
                            Modifier.height(
                                StoryNestDimens.Space32
                            )
                    )

// =================================================
// Reader Appearance
// =================================================

                    Text(
                        text = "Reader Appearance",
                        style =
                            MaterialTheme.typography.titleLarge,
                        color =
                            StoryNestPalette.TextPrimary
                    )

                    Spacer(
                        modifier =
                            Modifier.height(
                                StoryNestDimens.Space8
                            )
                    )

                    Text(
                        text =
                            "Choose the visual theme for reading stories.",
                        style =
                            MaterialTheme.typography.bodyMedium,
                        color =
                            StoryNestPalette.TextSecondary
                    )

                    Spacer(
                        modifier =
                            Modifier.height(
                                StoryNestDimens.Space16
                            )
                    )

                    StoryDropdown(label = "Reader Theme", value =
                            uiState.readerTheme.displayName(), items =
                            StoryTheme.entries.map {
                                it.displayName()
                            }, onSelected = { selected ->

                            val theme =
                                StoryTheme.entries.firstOrNull {
                                    it.displayName() == selected
                                }

                            if (theme != null) {

                                viewModel.onEvent(
                                    SettingsEvent.ReaderThemeChanged(
                                        theme
                                    )
                                )
                            }
                        }, modifier =
                            Modifier.fillMaxWidth())

                    // -------------------------------------------------
                    // Read Aloud
                    // -------------------------------------------------

                    SettingsSwitchRow(

                        title = "Read Aloud",

                        description =
                            "Enable story narration using text-to-speech.",

                        checked =
                            uiState.readAloudEnabled,

                        onCheckedChange = { enabled ->

                            viewModel.onEvent(
                                SettingsEvent
                                    .ReadAloudChanged(
                                        enabled
                                    )
                            )
                        }
                    )

                    // -------------------------------------------------
                    // Music
                    // -------------------------------------------------

                    SettingsSwitchRow(

                        title = "Background Music",

                        description =
                            "Play background music while reading stories.",

                        checked =
                            uiState.musicEnabled,

                        onCheckedChange = { enabled ->

                            viewModel.onEvent(
                                SettingsEvent
                                    .MusicChanged(
                                        enabled
                                    )
                            )
                        }
                    )

                    Spacer(
                        modifier =
                            Modifier.height(
                                StoryNestDimens.Space32
                            )
                    )

                    Text(
                        text =
                            "Your preferences are saved automatically.",
                        style =
                            MaterialTheme.typography
                                .bodySmall,
                        color =
                            StoryNestPalette.TextSecondary
                    )
                }
            }
        }
    }

}
private fun StoryTheme.displayName(): String {

    return name
        .lowercase()
        .replace('_', ' ')
        .replaceFirstChar {
            it.uppercase()
        }
}