package com.sd.storyteller.feature.category.ui

import com.sd.storyteller.domain.model.Story

/**
 * Created by SDHOLPURIA on 07-08-2026.
 */

data class CategoryUiState(

    val category: String = "",

    val stories: List<Story> = emptyList(),

    val isLoading: Boolean = true,

    val error: String? = null
)