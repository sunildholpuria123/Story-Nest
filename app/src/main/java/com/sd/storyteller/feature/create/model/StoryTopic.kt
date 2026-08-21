package com.sd.storyteller.feature.create.model

import kotlinx.serialization.Serializable

/**
 * Specific story subjects/themes.
 *
 * Created by SDHOLPURIA on 08-08-2026.
 */
@Serializable
data class StoryTopic(
    val id: String,
    val name: String,
    val emoji: String,
    val description: String = ""
)