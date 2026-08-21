package com.sd.storyteller.data.remote.model

/**
 * Created by SDHOLPURIA on 01-08-2026.
 */

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeminiRequest(

    @SerialName("contents")
    val contents: List<Content>
)

@Serializable
data class Content(

    @SerialName("parts")
    val parts: List<Part>
)

@Serializable
data class Part(

    @SerialName("text")
    val text: String
)