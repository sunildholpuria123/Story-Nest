package com.sd.storyteller.feature.create.model

/**
 * Created by SDHOLPURIA on 01-08-2026.
 */

data class StoryCategory(
    val id: String,
    val name: String,
    val emoji: String,
    val description: String = ""
)