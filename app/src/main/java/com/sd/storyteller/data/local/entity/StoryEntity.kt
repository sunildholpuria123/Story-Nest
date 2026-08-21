package com.sd.storyteller.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sd.storyteller.core.constants.StoryLanguage
import com.sd.storyteller.domain.model.Story
import com.sd.storyteller.feature.create.model.StoryTopic

/**
 * Created by SDHOLPURIA on 01-08-2026.
 */

@Entity(tableName = "stories")
data class StoryEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val title: String,

    val content: String,

    val characterName: String,

    val category: String,

    val mood: String,
    val topic: StoryTopic? = null,

    val isFavorite: Boolean = false,
    val language: String = StoryLanguage.HINDI.code,

    val createdAt: Long,

    val lastReadSentence: Int = -1
) {
    fun toDomain(): Story = Story(
        id = id,
        title = title,
        content = content,
        category = category,
        language =
            StoryLanguage.entries.firstOrNull {
                it.code == language
            } ?: StoryLanguage.HINDI,
        isFavorite = isFavorite,
        topic = topic,
        lastReadSentence = lastReadSentence
    )
}
