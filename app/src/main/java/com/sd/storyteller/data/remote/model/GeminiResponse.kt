package com.sd.storyteller.data.remote.model

/**
 * Created by SDHOLPURIA on 01-08-2026.
 */

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeminiResponse(

    @SerialName("candidates")
    val candidates: List<Candidate> = emptyList()
)

@Serializable
data class Candidate(

    @SerialName("content")
    val content: ContentResponse? = null,

    @SerialName("finishReason")
    val finishReason: String? = null
)

@Serializable
data class ContentResponse(

    @SerialName("parts")
    val parts: List<PartResponse> = emptyList()
)

@Serializable
data class PartResponse(

    @SerialName("text")
    val text: String = ""
)