package com.sd.storyteller.data.remote.model.story

/**
 * Created by SDHOLPURIA on 02-11-2026.
 */

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SupabaseStoryDto(
    val id: String,

    @SerialName("user_id")
    val userId: String,

    val title: String,
    val content: String,

    @SerialName("character_name")
    val characterName: String = "",

    val category: String,
    val mood: String = "",
    val topic: String? = null,
    val language: String = "hi",

    @SerialName("is_favorite")
    val isFavorite: Boolean = false,

    @SerialName("created_at")
    val createdAt: Long,

    @SerialName("last_read_sentence")
    val lastReadSentence: Int = -1,

    @SerialName("updated_at")
    val updatedAt: Long,
    val isDeleted: Boolean = false

)
