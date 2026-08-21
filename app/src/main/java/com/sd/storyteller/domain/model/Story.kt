package com.sd.storyteller.domain.model

import com.sd.storyteller.core.constants.StoryLanguage
import com.sd.storyteller.feature.create.model.StoryTopic

/**
 * Created by SDHOLPURIA on 01-08-2026.
 */


data class Story(

    val id: Long = 0,

    val title: String,

    val content: String,
    val category: String,
    val topic: StoryTopic? = null,
    val language: StoryLanguage = StoryLanguage.HINDI,

    val isFavorite: Boolean = false,
    val lastReadSentence: Int = -1,
)