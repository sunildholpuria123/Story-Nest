package com.sd.storyteller.feature.home.ui

/**
 * Created by SDHOLPURIA on 07-08-2026.
 */

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sd.storyteller.domain.usecase.GetStoriesUseCase
import com.sd.storyteller.feature.home.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getStories: GetStoriesUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(HomeUiState())

    val uiState =
        _uiState.asStateFlow()

    init {
        loadStories()
    }

    private fun loadStories() {

        viewModelScope.launch {

            getStories()
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