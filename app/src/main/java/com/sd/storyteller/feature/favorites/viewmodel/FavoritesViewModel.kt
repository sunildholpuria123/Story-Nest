package com.sd.storyteller.feature.favorites.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sd.storyteller.domain.model.Story
import com.sd.storyteller.domain.usecase.GetFavoriteStoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class FavoritesUiState(
    val stories: List<Story> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteStories: GetFavoriteStoriesUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(FavoritesUiState())

    val uiState =
        _uiState.asStateFlow()

    init {
        loadFavorites()
    }

    private fun loadFavorites() {

        viewModelScope.launch {

            getFavoriteStories()
                .catch { throwable ->

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error =
                                throwable.message
                                    ?: "Unable to load favorites."
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
}