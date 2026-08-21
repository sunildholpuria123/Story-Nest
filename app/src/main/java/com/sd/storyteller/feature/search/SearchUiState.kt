package com.sd.storyteller.feature.search

/**
 * Created by SDHOLPURIA on 07-08-2026.
 */

import com.sd.storyteller.domain.model.Story

data class SearchUiState(
    val query: String = "",
    val stories: List<Story> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)