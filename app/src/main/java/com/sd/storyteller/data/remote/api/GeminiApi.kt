package com.sd.storyteller.data.remote.api

/**
 * Created by SDHOLPURIA on 01-08-2026.
 */
import com.sd.storyteller.data.remote.model.GeminiRequest
import com.sd.storyteller.data.remote.model.GeminiResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface GeminiApi {

    @POST("v1beta/models/gemini-flash-latest:generateContent")
    suspend fun generateStory(

        @Query("key")
        apiKey: String,

        @Body
        request: GeminiRequest

    ): GeminiResponse
}