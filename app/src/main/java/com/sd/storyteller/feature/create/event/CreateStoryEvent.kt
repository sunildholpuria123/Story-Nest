package com.sd.storyteller.feature.create.event

import com.sd.storyteller.core.constants.StoryLanguage
import com.sd.storyteller.feature.create.model.StoryCategory
import com.sd.storyteller.feature.create.model.StoryLength
import com.sd.storyteller.feature.create.model.StoryMood
import com.sd.storyteller.feature.create.model.StoryTopic

/**
 * Created by SDHOLPURIA on 01-08-2026.
 */

sealed interface CreateStoryEvent {

    data class CharacterChanged(
        val value: String
    ) : CreateStoryEvent

    data class AgeChanged(
        val value: String
    ) : CreateStoryEvent

    data class CategoryChanged(
        val value: StoryCategory
    ) : CreateStoryEvent

    data class LengthChanged(
        val value: StoryLength
    ) : CreateStoryEvent

    data class MoodChanged(
        val value: StoryMood
    ) : CreateStoryEvent

    data object GenerateStory : CreateStoryEvent

    data class StoryLanguageChanged(
        val value: StoryLanguage
    ) : CreateStoryEvent

    data class TopicChanged(
        val value: StoryTopic?
    ) : CreateStoryEvent
}