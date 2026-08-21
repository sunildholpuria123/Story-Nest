package com.sd.storyteller.feature.create.state

import com.sd.storyteller.core.constants.StoryCategories
import com.sd.storyteller.core.constants.StoryLanguage
import com.sd.storyteller.feature.create.model.StoryCategory
import com.sd.storyteller.feature.create.model.StoryLength
import com.sd.storyteller.feature.create.model.StoryMood
import com.sd.storyteller.feature.create.model.StoryTopic

/**
 * Created by SDHOLPURIA on 01-08-2026.
 */


data class CreateStoryUiState(

    val characterName: String = "",

    val age: String = "",

    val category: StoryCategory = StoryCategories.all[0],

    val length: StoryLength = StoryLength.Short,

    val mood: StoryMood = StoryMood.Happy,
    val language: StoryLanguage = StoryLanguage.HINDI,
    val error: String? = null,
    val topic: StoryTopic? = null,
    val remainingAttempts: Int = 3,

    val isLoading: Boolean = false
)