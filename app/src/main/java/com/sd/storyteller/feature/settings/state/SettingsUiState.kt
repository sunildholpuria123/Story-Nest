package com.sd.storyteller.feature.settings.state

import com.sd.storyteller.core.constants.StoryLanguage
import com.sd.storyteller.ui.theme.StoryTheme

/**
 * Created by SDHOLPURIA on 08-08-2026.
 */

data class SettingsUiState(

    val language: StoryLanguage =
        StoryLanguage.HINDI,

    val readAloudEnabled: Boolean =
        true,

    val musicEnabled: Boolean =
        false,

    val readerTheme: StoryTheme =
        StoryTheme.BEDTIME,

    val isLoading: Boolean =
        true,

    val error: String? =
        null
)