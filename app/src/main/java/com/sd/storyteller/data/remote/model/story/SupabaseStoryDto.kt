package com.sd.storyteller.data.remote.model.story

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

    // Supabase timestamptz values are ISO-8601 strings, not epoch millis.
    @SerialName("created_at")
    val createdAt: String,

    @SerialName("last_read_sentence")
    val lastReadSentence: Int = -1,

    @SerialName("updated_at")
    val updatedAt: String,

    @SerialName("is_deleted")
    val isDeleted: Boolean = false
)
