package com.sd.storyteller.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoryNestGenerateRequest(
    @SerialName("prompt")
    val prompt: String
)
