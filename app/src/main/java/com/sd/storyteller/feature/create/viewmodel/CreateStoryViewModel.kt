package com.sd.storyteller.feature.create.viewmodel

/**
 * Created by SDHOLPURIA on 01-08-2026.
 */

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sd.storyteller.core.setting.SettingsRepository
import com.sd.storyteller.domain.model.Story
import com.sd.storyteller.domain.model.StoryRequest
import com.sd.storyteller.domain.usecase.GenerateStoryUseCase
import com.sd.storyteller.feature.create.event.CreateStoryEvent
import com.sd.storyteller.feature.create.state.CreateStoryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CreateStoryViewModel @Inject constructor(
    private val generateStoryUseCase: GenerateStoryUseCase,
    private val settingsRepository: SettingsRepository

) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            CreateStoryUiState()
        )

    val uiState: StateFlow<CreateStoryUiState> =
        _uiState.asStateFlow()
    init {
        loadRemainingAttempts()
    }

    /**
     * Emits the generated story only when
     * generation succeeds.
     */
    private val _story =
        MutableSharedFlow<Story>(
            extraBufferCapacity = 1
        )

    val story =
        _story.asSharedFlow()

    // =========================================================
    // Events
    // =========================================================

    fun onEvent(
        event: CreateStoryEvent
    ) {

        when (event) {

            // -------------------------------------------------
            // Character
            // -------------------------------------------------

            is CreateStoryEvent.CharacterChanged -> {

                _uiState.update {

                    it.copy(
                        characterName =
                            event.value,
                        error = null
                    )
                }
            }

            // -------------------------------------------------
            // Age
            // -------------------------------------------------

            is CreateStoryEvent.AgeChanged -> {

                /*
                 * Allow only digits while typing.
                 *
                 * Empty value is also allowed temporarily so
                 * the user can clear the field.
                 */
                if (
                    event.value.isEmpty() ||
                    event.value.all { it.isDigit() }
                ) {

                    _uiState.update {

                        it.copy(
                            age = event.value,
                            error = null
                        )
                    }
                }
            }

            // -------------------------------------------------
            // Category
            // -------------------------------------------------

            is CreateStoryEvent.CategoryChanged -> {

                _uiState.update {

                    it.copy(
                        category =
                            event.value,
                        error = null,
                        topic = null

                    )
                }
            }

            // -------------------------------------------------
            // Length
            // -------------------------------------------------

            is CreateStoryEvent.LengthChanged -> {

                _uiState.update {

                    it.copy(
                        length =
                            event.value,
                        error = null
                    )
                }
            }

            // -------------------------------------------------
            // Mood
            // -------------------------------------------------

            is CreateStoryEvent.MoodChanged -> {

                _uiState.update {

                    it.copy(
                        mood =
                            event.value,
                        error = null
                    )
                }
            }

            // -------------------------------------------------
            // Story Language
            // -------------------------------------------------

            is CreateStoryEvent.StoryLanguageChanged -> {

                _uiState.update {

                    it.copy(
                        language =
                            event.value,
                        error = null
                    )
                }
            }

            is CreateStoryEvent.TopicChanged -> {

                _uiState.update {

                    it.copy(
                        topic = event.value,
                        error = null
                    )
                }
            }

            // -------------------------------------------------
            // Generate Story
            // -------------------------------------------------

            CreateStoryEvent.GenerateStory -> {

                generateStory()
            }
        }
    }

    // =========================================================
    // Generate
    // =========================================================

    private fun generateStory() {

        // ---------------------------------------------------------
        // Prevent multiple requests
        // ---------------------------------------------------------

        if (_uiState.value.isLoading) {
            return
        }

        val state =
            _uiState.value

        // ---------------------------------------------------------
        // Validate
        // ---------------------------------------------------------

        val validationError =
            validate(state)

        if (validationError != null) {

            _uiState.update {
                it.copy(
                    error = validationError
                )
            }

            return
        }

        // ---------------------------------------------------------
        // Daily Story Limit
        // ---------------------------------------------------------

        val remainingAttempts =
            settingsRepository
                .getRemainingStoryAttempts()

        if (remainingAttempts <= 0) {

            _uiState.update {

                it.copy(
                    error =
                        "You have used all 3 story attempts for today. " +
                                "Please try again tomorrow.",
                    remainingAttempts = 0
                )
            }

            return
        }

        // ---------------------------------------------------------
        // Consume one attempt
        // ---------------------------------------------------------

        settingsRepository
            .incrementStoryGenerationAttempt()

        val updatedRemainingAttempts =
            settingsRepository
                .getRemainingStoryAttempts()

        _uiState.update {

            it.copy(
                isLoading = true,
                error = null,
                remainingAttempts =
                    updatedRemainingAttempts
            )
        }

        // ---------------------------------------------------------
        // Generate
        // ---------------------------------------------------------

        val age =
            state.age.toInt()

        viewModelScope.launch {

            val request =
                StoryRequest(

                    characterName =
                        state.characterName.trim(),

                    age =
                        age,

                    category =
                        state.category,

                    length =
                        state.length,

                    mood =
                        state.mood,

                    topic =
                        state.topic,

                    language =
                        settingsRepository.getLanguage()
                )

            try {

                val result =
                    generateStoryUseCase(request)

                result
                    .onSuccess { story ->

                        _story.emit(story)
                    }

                    .onFailure { throwable ->

                        _uiState.update {

                            it.copy(
                                error =
                                    getGenerationErrorMessage(
                                        throwable
                                    )
                            )
                        }
                    }

            } catch (throwable: Throwable) {

                _uiState.update {

                    it.copy(
                        error =
                            getGenerationErrorMessage(
                                throwable
                            )
                    )
                }

            } finally {

                _uiState.update {

                    it.copy(
                        isLoading = false
                    )
                }
            }
        }
    }
    // =========================================================
    // Validation
    // =========================================================

    private fun validate(
        state: CreateStoryUiState
    ): String? {

        val characterName =
            state.characterName.trim()

        if (characterName.isBlank()) {

            return "Please enter a character name."
        }

        /*
         * Prevent extremely long character names from
         * being sent unnecessarily to Gemini.
         */
        if (characterName.length > 50) {

            return "Character name must be 50 characters or less."
        }

        val ageText =
            state.age.trim()

        if (ageText.isBlank()) {

            return "Please enter the child's age."
        }

        val age =
            ageText.toIntOrNull()

        if (age == null) {

            return "Please enter a valid age."
        }

        if (age !in 3..12) {

            return "Age must be between 3 and 12 years."
        }

        return null
    }

    // =========================================================
    // Error
    // =========================================================

    fun clearError() {

        _uiState.update {

            it.copy(
                error = null
            )
        }
    }

    private fun getGenerationErrorMessage(
        throwable: Throwable
    ): String {

        return when (throwable) {

            is java.net.UnknownHostException -> {

                "No internet connection. " +
                        "Please check your connection and try again."
            }

            is java.net.SocketTimeoutException -> {

                "The request took too long. " +
                        "Please try again."
            }

            is java.io.IOException -> {

                "Network error. " +
                        "Please check your connection and try again."
            }

            else -> {

                throwable.message
                    ?.takeIf {
                        it.isNotBlank()
                    }
                    ?: "Unable to create the story. " +
                    "Please try again."
            }
        }
    }

    private fun loadRemainingAttempts() {

        _uiState.update {

            it.copy(
                remainingAttempts =
                    settingsRepository
                        .getRemainingStoryAttempts()
            )
        }
    }
}