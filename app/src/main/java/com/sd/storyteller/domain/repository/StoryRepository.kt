package com.sd.storyteller.domain.repository

import com.sd.storyteller.domain.model.Story
import com.sd.storyteller.domain.model.StoryRequest

/**
 * Created by SDHOLPURIA on 01-08-2026.
 */


import kotlinx.coroutines.flow.Flow

interface StoryRepository {

    suspend fun generateStory(
        request: StoryRequest
    ): Result<Story>

    fun getStories(): Flow<List<Story>>

    suspend fun getStory(
        id: Long
    ): Story?

    suspend fun deleteStory(
        id: Long
    )

    fun getFavoriteStories(): Flow<List<Story>>

    suspend fun updateFavorite(
        id: Long,
        favorite: Boolean
    )
    suspend fun updateReadingPosition(
        storyId: Long,
        sentence: Int
    )


    fun observeStoriesByCategory(
        category: String
    ): Flow<List<Story>>
}