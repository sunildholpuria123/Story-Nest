
package com.sd.storyteller.data.remote.api

import com.sd.storyteller.data.remote.model.GeminiResponse
import com.sd.storyteller.data.remote.model.StoryNestGenerateRequest
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface GeminiApi {

    @POST("api/v1/stories/generate")
    suspend fun generateStory(
        @Header("Authorization") authorization: String,
        @Body request: StoryNestGenerateRequest
    ): GeminiResponse
}
