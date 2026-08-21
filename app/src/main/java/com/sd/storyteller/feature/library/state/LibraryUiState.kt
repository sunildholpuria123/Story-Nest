package com.sd.storyteller.feature.library.state

import com.sd.storyteller.domain.model.Story
import com.sd.storyteller.feature.library.ui.LibraryTab

/**
 * Created by SDHOLPURIA on 07-08-2026.
 */

data class LibraryUiState(

    val selectedTab: LibraryTab = LibraryTab.ALL,

    val stories: List<Story> = emptyList(),

    val isLoading: Boolean = true,

    val error: String? = null
) {

    val visibleStories: List<Story>
        get() {

            return when (selectedTab) {

                LibraryTab.ALL ->
                    stories

                LibraryTab.FAVORITES ->
                    stories.filter {
                        it.isFavorite
                    }

                LibraryTab.HISTORY ->
                    stories.filter {
                        it.lastReadSentence >= 0
                    }
            }
        }
}