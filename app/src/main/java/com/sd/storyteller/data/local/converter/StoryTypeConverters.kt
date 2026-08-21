package com.sd.storyteller.data.local.converter

import androidx.room.TypeConverter
import com.sd.storyteller.feature.create.model.StoryTopic
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class StoryTypeConverters {
    @TypeConverter
    fun fromStoryTopic(topic: StoryTopic?): String? {
        return topic?.let { Json.encodeToString(it) }
    }

    @TypeConverter
    fun toStoryTopic(topicJson: String?): StoryTopic? {
        return topicJson?.let { Json.decodeFromString(it) }
    }
}
