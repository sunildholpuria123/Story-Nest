package com.sd.storyteller.feature.settings.event

import com.sd.storyteller.core.constants.StoryLanguage
import com.sd.storyteller.ui.theme.StoryTheme

/**
 * Created by SDHOLPURIA on 08-08-2026.
 */

sealed interface SettingsEvent {

    data class LanguageChanged(
        val language: StoryLanguage
    ) : SettingsEvent

    data class ReadAloudChanged(
        val enabled: Boolean
    ) : SettingsEvent

    data class MusicChanged(
        val enabled: Boolean
    ) : SettingsEvent

    data class ReaderThemeChanged(
        val theme: StoryTheme
    ) : SettingsEvent

    data object Retry : SettingsEvent
}