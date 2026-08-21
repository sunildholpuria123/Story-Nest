package com.sd.storyteller.feature.search

/**
 * Created by SDHOLPURIA on 07-08-2026.
 */

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sd.storyteller.domain.usecase.GetStoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getStories: GetStoriesUseCase
) : ViewModel() {

    private val query =
        MutableStateFlow("")

    private val _uiState =
        MutableStateFlow(SearchUiState())

    val uiState =
        _uiState.asStateFlow()

    init {
        observeSearch()
    }

    private fun observeSearch() {

        viewModelScope.launch {

            query
                .debounce(300)
                .distinctUntilChanged()
                .flatMapLatest { searchQuery ->

                    getStories()
                        .map { stories ->

                            if (searchQuery.isBlank()) {
                                stories
                            } else {
                                stories.filter { story ->

                                    story.title.contains(
                                        searchQuery,
                                        ignoreCase = true
                                    ) ||
                                            story.content.contains(
                                                searchQuery,
                                                ignoreCase = true
                                            ) ||
                                            story.category.contains(
                                                searchQuery,
                                                ignoreCase = true
                                            )
                                }
                            }
                        }
                }
                .catch { throwable ->

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error =
                                throwable.message
                                    ?: "Unable to search stories."
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

    fun updateQuery(value: String) {

        query.value = value

        _uiState.update {
            it.copy(
                query = value,
                isLoading = value.isNotBlank()
            )
        }
    }

    fun clearSearch() {
        updateQuery("")
    }
}