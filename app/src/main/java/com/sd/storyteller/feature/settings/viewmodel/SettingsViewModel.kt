package com.sd.storyteller.feature.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sd.storyteller.core.setting.SettingsRepository
import com.sd.storyteller.feature.settings.event.SettingsEvent
import com.sd.storyteller.feature.settings.state.SettingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by SDHOLPURIA on 08-08-2026.
 */

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            SettingsUiState()
        )

    val uiState =
        _uiState.asStateFlow()

    init {
        loadSettings()
    }

    // ---------------------------------------------------------
    // Load Settings
    // ---------------------------------------------------------

    private fun loadSettings() {

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            try {

                val settings =
                    settingsRepository.getSettings()

                _uiState.update {

                    it.copy(

                        language =
                            settings.language,

                        readAloudEnabled =
                            settings.readAloudEnabled,

                        musicEnabled =
                            settings.musicEnabled,

                        readerTheme =
                            settings.readerTheme,

                        isLoading = false,

                        error = null
                    )
                }

            } catch (exception: Exception) {

                _uiState.update {

                    it.copy(
                        isLoading = false,
                        error =
                            exception.message
                                ?: "Unable to load settings."
                    )
                }
            }
        }
    }

    // ---------------------------------------------------------
    // Events
    // ---------------------------------------------------------

    fun onEvent(
        event: SettingsEvent
    ) {

        when (event) {

            // -------------------------------------------------
            // Language
            // -------------------------------------------------

            is SettingsEvent.LanguageChanged -> {

                settingsRepository.setLanguage(
                    event.language
                )

                _uiState.update {

                    it.copy(
                        language =
                            event.language
                    )
                }
            }

            // -------------------------------------------------
            // Read Aloud
            // -------------------------------------------------

            is SettingsEvent.ReadAloudChanged -> {

                settingsRepository
                    .setReadAloudEnabled(
                        event.enabled
                    )

                _uiState.update {

                    it.copy(
                        readAloudEnabled =
                            event.enabled
                    )
                }
            }

            // -------------------------------------------------
            // Music
            // -------------------------------------------------

            is SettingsEvent.MusicChanged -> {

                settingsRepository
                    .setMusicEnabled(
                        event.enabled
                    )

                _uiState.update {

                    it.copy(
                        musicEnabled =
                            event.enabled
                    )
                }
            }

            // -------------------------------------------------
            // Reader Theme
            // -------------------------------------------------

            is SettingsEvent.ReaderThemeChanged -> {

                settingsRepository
                    .setReaderTheme(
                        event.theme
                    )

                _uiState.update {

                    it.copy(
                        readerTheme =
                            event.theme
                    )
                }
            }

            // -------------------------------------------------
            // Retry
            // -------------------------------------------------

            SettingsEvent.Retry -> {
                loadSettings()
            }
        }
    }
}