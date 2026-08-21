package com.sd.storyteller.feature.category.ui

/**
 * Created by SDHOLPURIA on 07-08-2026.
 */

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sd.storyteller.domain.usecase.GetStoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CategoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getStories: GetStoriesUseCase
) : ViewModel() {

    private val category: String =
        checkNotNull(
            savedStateHandle["category"]
        )

    private val _uiState =
        MutableStateFlow(
            CategoryUiState(
                category = category
            )
        )

    val uiState =
        _uiState.asStateFlow()

    init {
        loadStories()
    }

    private fun loadStories() {

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            getStories(category)
                .catch { throwable ->

                    _uiState.update {

                        it.copy(
                            isLoading = false,
                            error =
                                throwable.message
                                    ?: "Unable to load stories."
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

    fun retry() {
        loadStories()
    }
}