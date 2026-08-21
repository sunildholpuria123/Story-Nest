package com.sd.storyteller.feature.home.state

/**
 * Created by SDHOLPURIA on 07-08-2026.
 */

import com.sd.storyteller.domain.model.Story

data class HomeUiState(
    val stories: List<Story> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
) {
    val featuredStories: List<Story>
        get() = stories.take(5)

    val recentStories: List<Story>
        get() = stories
            .filter { it.lastReadSentence >= 0 }
            .take(5)
}