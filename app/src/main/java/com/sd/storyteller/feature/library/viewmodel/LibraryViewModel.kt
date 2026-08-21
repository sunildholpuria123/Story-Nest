package com.sd.storyteller.feature.library.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sd.storyteller.domain.usecase.DeleteStoryUseCase
import com.sd.storyteller.domain.usecase.GetStoriesUseCase
import com.sd.storyteller.feature.library.state.LibraryUiState
import com.sd.storyteller.feature.library.ui.LibraryTab
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by SDHOLPURIA on 07-08-2026.
 */

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val getStories: GetStoriesUseCase,
    private val deleteStory: DeleteStoryUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            LibraryUiState()
        )

    val uiState =
        _uiState.asStateFlow()

    init {
        loadStories()
    }

    // ---------------------------------------------------------
    // Load Stories
    // ---------------------------------------------------------

    private fun loadStories() {

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            getStories()
                .catch { throwable ->

                    _uiState.update {

                        it.copy(
                            isLoading = false,
                            error =
                                throwable.message
                                    ?: "Unable to load library."
                        )
                    }
                }
                .collect { stories ->

                    _uiState.update {

                        it.copy(
                            stories = stories,
                            isLoading = false,
                            error = null
                        )
                    }
                }
        }
    }

    // ---------------------------------------------------------
    // Tab
    // ---------------------------------------------------------

    fun selectTab(
        tab: LibraryTab
    ) {

        _uiState.update {

            it.copy(
                selectedTab = tab
            )
        }
    }

    // ---------------------------------------------------------
    // Delete Story
    // ---------------------------------------------------------

    fun deleteStory(
        storyId: Long
    ) {

        viewModelScope.launch {

            try {

                deleteStory.invoke(
                    storyId
                )

                /*
                 * No manual removal from the list is required.
                 *
                 * GetStoriesUseCase exposes a Flow, so Room
                 * automatically emits the updated list after
                 * deletion.
                 */

                _uiState.update {
                    it.copy(
                        error = null
                    )
                }

            } catch (exception: Exception) {

                _uiState.update {

                    it.copy(
                        error =
                            exception.message
                                ?: "Unable to delete story."
                    )
                }
            }
        }
    }

    // ---------------------------------------------------------
    // Retry
    // ---------------------------------------------------------

    fun retry() {

        if (_uiState.value.isLoading) {
            return
        }

        loadStories()
    }
}