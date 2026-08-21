package com.sd.storyteller.feature.reader.ui

/**
 * Created by SDHOLPURIA on 01-08-2026.
 */

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sd.storyteller.core.audio.StoryAudioPlayer
import com.sd.storyteller.core.setting.SettingsRepository
import com.sd.storyteller.core.tts.SpeechStyleMapper
import com.sd.storyteller.core.tts.StoryTextToSpeech
import com.sd.storyteller.domain.usecase.GetStoryUseCase
import com.sd.storyteller.domain.usecase.UpdateFavoriteUseCase
import com.sd.storyteller.domain.usecase.UpdateReadingPositionUseCase
import com.sd.storyteller.ui.theme.StoryTheme
import com.sd.storyteller.ui.theme.StoryThemeMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ReaderViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val tts: StoryTextToSpeech,
    private val getStory: GetStoryUseCase,
    private val updateFavorite: UpdateFavoriteUseCase,
    private val updateReadingPositionUseCase: UpdateReadingPositionUseCase,
    private val audioPlayer: StoryAudioPlayer,
    private val settingsRepository: SettingsRepository

) : ViewModel() {

    private val storyId: Long =
        checkNotNull(
            savedStateHandle["storyId"]
        )

    private val _uiState =
        MutableStateFlow(
            ReaderUiState()
        )

    val uiState =
        _uiState.asStateFlow()

    private var readingPositionJob: Job? = null

    init {
        observeTts()
        loadStory()
//        toggleMusic()
    }

    // =========================================================
    // Story
    // =========================================================

    private fun loadStory() {

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            try {

                val story =
                    getStory(storyId)

                if (story == null) {

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "Story not found."
                        )
                    }

                    return@launch
                }
                val theme =
                    StoryThemeMapper.fromCategory(
                        story.category
                    )
                _uiState.update {

                    it.copy(

                        id =
                            story.id,

                        title =
                            story.title,

                        content =
                            story.content,

                        isFavorite =
                            story.isFavorite,

                        language =
                            story.language,

                        /*
                         * Restore the last saved
                         * reading position.
                         */
                        currentSentence =
                            story.lastReadSentence
                                .coerceAtLeast(0),
                        category = story.category,
                        theme = theme,

                        isLoading =
                            false,

                        error =
                            null,

                        )
                }
                toggleMusic(theme)
                tts.setSpeechStyle(
                    SpeechStyleMapper.fromTheme(
                        theme
                    )
                )

            } catch (exception: Exception) {

                _uiState.update {

                    it.copy(

                        isLoading =
                            false,

                        error =
                            exception.message
                                ?: "Unable to load story."
                    )
                }
            }
        }
    }

    fun retry() {

        if (_uiState.value.isLoading) {
            return
        }

        loadStory()
    }

    // =========================================================
    // TTS
    // =========================================================

    private fun observeTts() {

        // -----------------------------------------------------
        // TTS Ready
        // -----------------------------------------------------

        tts.setOnReadyChanged { ready ->

            _uiState.update {

                it.copy(
                    ttsReady = ready
                )
            }
        }

        // -----------------------------------------------------
        // Sentence Changed
        // -----------------------------------------------------

        tts.setOnSentenceChanged { sentenceIndex ->

            val state =
                _uiState.value

            val safeSentence =
                if (state.totalSentences > 0) {

                    sentenceIndex.coerceIn(
                        0,
                        state.totalSentences - 1
                    )

                } else {

                    sentenceIndex.coerceAtLeast(0)
                }

            _uiState.update {

                it.copy(
                    currentSentence =
                        safeSentence
                )
            }

            /*
             * Save position after a short delay.
             *
             * This prevents excessive database writes
             * while TTS is moving through sentences.
             */
            saveReadingPositionDebounced(
                safeSentence
            )
        }

        // -----------------------------------------------------
        // TTS Completed
        // -----------------------------------------------------

        tts.setOnCompleted {

            val state =
                _uiState.value

            /*
             * Keep the last valid sentence.
             *
             * Example:
             * 10 sentences -> last index = 9
             */
            val lastSentence =
                if (state.totalSentences > 0) {

                    (
                            state.totalSentences - 1
                            ).coerceAtLeast(0)

                } else {

                    state.currentSentence
                        .coerceAtLeast(0)
                }

            /*
             * Keep the completed position.
             *
             * Do not reset database position to 0.
             */
            saveReadingPositionImmediately(
                lastSentence
            )

            _uiState.update {

                it.copy(

                    isSpeaking =
                        false,

                    isPaused =
                        false,

                    currentSentence =
                        lastSentence
                )
            }
        }
    }

    // =========================================================
    // Reading Position
    // =========================================================

    /**
     * Saves reading position after a short delay.
     *
     * This avoids excessive database writes while
     * TTS moves through the story.
     */
    private fun saveReadingPositionDebounced(
        sentence: Int
    ) {

        val safeSentence =
            sentence.coerceAtLeast(0)

        readingPositionJob?.cancel()

        readingPositionJob =
            viewModelScope.launch {

                delay(500L)

                saveReadingPositionImmediately(
                    safeSentence
                )
            }
    }

    /**
     * Immediately saves reading position.
     */
    private fun saveReadingPositionImmediately(
        sentence: Int
    ) {

        val safeSentence =
            sentence.coerceAtLeast(0)

        readingPositionJob?.cancel()

        readingPositionJob =
            viewModelScope.launch {

                try {

                    updateReadingPositionUseCase(

                        storyId =
                            storyId,

                        sentence =
                            safeSentence
                    )

                } catch (_: Exception) {

                    /*
                     * Reading-position persistence
                     * must never crash Reader.
                     */
                }
            }
    }

    // =========================================================
    // Play
    // =========================================================

    /**
     * Starts reading from the current sentence.
     *
     * TTS determines the sentence count after playback
     * preparation.
     */
    fun play() {

        val state =
            _uiState.value

        // -----------------------------------------------------
        // TTS must be ready
        // -----------------------------------------------------

        if (!state.ttsReady) {
            return
        }

        // -----------------------------------------------------
        // Story must contain content
        // -----------------------------------------------------

        if (state.content.isBlank()) {
            return
        }

        // -----------------------------------------------------
        // Restore saved/current position
        // -----------------------------------------------------

        val startSentence =
            state.currentSentence
                .coerceAtLeast(0)

        // -----------------------------------------------------
        // Start TTS
        // -----------------------------------------------------

        val started =
            tts.play(

                text =
                    state.content,

                language =
                    state.language,

                startSentence =
                    startSentence
            )

        if (!started) {
            return
        }

        // -----------------------------------------------------
        // Get sentence count from TTS
        // -----------------------------------------------------

        val totalSentences =
            tts.getSentenceCount()

        // -----------------------------------------------------
        // Make sure current sentence is valid
        // -----------------------------------------------------

        val safeStartSentence =
            if (totalSentences > 0) {

                startSentence.coerceIn(
                    0,
                    totalSentences - 1
                )

            } else {

                0
            }

        // -----------------------------------------------------
        // Update UI
        // -----------------------------------------------------

        _uiState.update {

            it.copy(

                isSpeaking =
                    true,

                isPaused =
                    false,

                currentSentence =
                    safeStartSentence,

                totalSentences =
                    totalSentences
            )
        }
    }

    // =========================================================
    // Pause
    // =========================================================

    /**
     * Pauses the story at the current sentence.
     */
    fun pause() {

        val state =
            _uiState.value

        if (!state.isSpeaking) {
            return
        }

        saveReadingPositionImmediately(
            state.currentSentence
        )

        tts.pause()

        _uiState.update {

            it.copy(

                isSpeaking =
                    false,

                isPaused =
                    true
            )
        }
    }

    // =========================================================
    // Resume
    // =========================================================

    /**
     * Resumes TTS from the paused position.
     */
    fun resume() {

        val state =
            _uiState.value

        if (!state.isPaused) {
            return
        }

        if (!state.ttsReady) {
            return
        }

        val resumed =
            tts.resume()

        if (!resumed) {
            return
        }

        _uiState.update {

            it.copy(

                isSpeaking =
                    true,

                isPaused =
                    false
            )
        }
    }

    // =========================================================
    // Stop
    // =========================================================

    /**
     * Completely stops TTS.
     *
     * The current reading position is preserved both
     * in the UI and database.
     *
     * Pressing Play again continues from this position.
     */
    fun stop() {

        val currentSentence =
            _uiState.value.currentSentence
                .coerceAtLeast(0)

        /*
         * Save current position.
         *
         * Never reset the saved position to 0.
         */
        saveReadingPositionImmediately(
            currentSentence
        )

        tts.stop()

        _uiState.update {

            it.copy(

                isSpeaking =
                    false,

                isPaused =
                    false,

                /*
                 * Keep current sentence.
                 */
                currentSentence =
                    currentSentence
            )
        }
    }

    // =========================================================
    // Favorite
    // =========================================================

    fun toggleFavorite() {

        viewModelScope.launch {

            val currentState =
                _uiState.value

            val favorite =
                !currentState.isFavorite

            try {

                updateFavorite(
                    currentState.id,
                    favorite
                )

                _uiState.update {

                    it.copy(
                        isFavorite =
                            favorite
                    )
                }

            } catch (_: Exception) {

                /*
                 * Keep existing UI state if
                 * database update fails.
                 */
            }
        }
    }

    // =========================================================
    // Music
    // =========================================================

    fun toggleMusic(theme: StoryTheme) {

//        val enabled =
//            !_uiState.value.musicEnabled
        val enabled =
            settingsRepository.isMusicEnabled()

        if (enabled) {

            audioPlayer.play(theme)

        } else {

            audioPlayer.stop()
        }

        _uiState.update {

            it.copy(
                musicEnabled =
                    enabled
            )
        }
    }

    // =========================================================
    // Back
    // =========================================================

    /**
     * Called by ReaderScreen before navigating away.
     */
    fun onBack() {

        saveReadingPositionImmediately(
            _uiState.value.currentSentence
        )

        audioPlayer.stop()

        tts.stop()
    }

    // =========================================================
    // Lifecycle
    // =========================================================

    override fun onCleared() {

        readingPositionJob?.cancel()

        audioPlayer.stop()

        tts.stop()

//        tts.shutdown()

        super.onCleared()
    }
}
