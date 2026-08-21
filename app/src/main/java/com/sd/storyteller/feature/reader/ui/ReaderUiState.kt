package com.sd.storyteller.feature.reader.ui

import com.sd.storyteller.core.constants.StoryLanguage
import com.sd.storyteller.feature.reader.model.ReadingState
import com.sd.storyteller.ui.theme.StoryTheme

/**
 * Created by SDHOLPURIA on 01-08-2026.
 */


data class ReaderUiState(

    val id: Long = 0,

    val title: String = "",

    val content: String = "",

    val isFavorite: Boolean = false,

    val isLoading: Boolean = true,
    val musicEnabled: Boolean = false,
    val isSpeaking: Boolean = false,

    val isPaused: Boolean = false,

    val currentSentence: Int = -1,
    val theme: StoryTheme =
        StoryTheme.BEDTIME,
    val readingState: ReadingState = ReadingState.IDLE,
    val totalSentences: Int = 0,
    val ttsReady: Boolean = false,
    val language: StoryLanguage =
        StoryLanguage.HINDI,
    val category: String = "",
    val error: String? = null


)