package com.sd.storyteller.feature.reader.ui

/**
 * Created by SDHOLPURIA on 07-08-2026.
 */

sealed interface ReaderUiEvent {

    data object ToggleFavorite : ReaderUiEvent

    data object ReadAloud : ReaderUiEvent

    data object PauseReading : ReaderUiEvent

    data object StopReading : ReaderUiEvent

    data object ResumeReading : ReaderUiEvent

    data object ToggleMusic : ReaderUiEvent
}