package com.sd.storyteller.domain.model

import com.sd.storyteller.core.constants.StoryLanguage
import com.sd.storyteller.feature.create.model.StoryCategory
import com.sd.storyteller.feature.create.model.StoryLength
import com.sd.storyteller.feature.create.model.StoryMood
import com.sd.storyteller.feature.create.model.StoryTopic

/**
 * Created by SDHOLPURIA on 01-08-2026.
 */

data class StoryRequest(

    val characterName: String,

    val age: Int,

    val category: StoryCategory,

    val length: StoryLength,

    val mood: StoryMood,
    val language: StoryLanguage = StoryLanguage.HINDI,
    val topic: StoryTopic? = null

)