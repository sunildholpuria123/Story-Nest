package com.sd.storyteller.data.remote.api

/**
 * Created by SDHOLPURIA on 02-11-2026.
 */

import com.sd.storyteller.data.remote.model.story.SupabaseStoryDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface SupabaseStoryApi {

    @POST("rest/v1/user_stories")
    suspend fun upsertStories(
        @Header("apikey") apiKey: String,
        @Header("Authorization") authorization: String,
        @Header("Prefer") prefer: String = "resolution=merge-duplicates,return=minimal",
        @Body stories: List<SupabaseStoryDto>
    ): Response<Unit>

    @GET("rest/v1/user_stories")
    suspend fun getUserStories(
        @Header("apikey") apiKey: String,
        @Header("Authorization") authorization: String,
        @Query("user_id") userIdFilter: String,
        @Query("order") order: String = "created_at.desc"
    ): Response<List<SupabaseStoryDto>>
}
