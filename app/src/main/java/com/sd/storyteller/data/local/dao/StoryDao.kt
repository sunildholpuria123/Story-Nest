package com.sd.storyteller.data.local.dao

/**
 * Created by SDHOLPURIA on 01-08-2026.
 */

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sd.storyteller.data.local.entity.StoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(
        story: StoryEntity
    )

    @Query(
        """
        SELECT *
        FROM stories
        ORDER BY createdAt DESC
    """
    )
    fun getStories(): Flow<List<StoryEntity>>

    @Query(
        """
        SELECT *
        FROM stories
        WHERE id = :id
    """
    )
    suspend fun getStory(
        id: Long
    ): StoryEntity?

    @Query(
        """
        DELETE FROM stories
        WHERE id = :id
    """
    )
    suspend fun delete(
        id: Long
    )

    @Query("DELETE FROM stories")
    suspend fun deleteAll()

    @Query(
        """
UPDATE stories
SET isFavorite = :favorite
WHERE id = :id
"""
    )
    suspend fun updateFavorite(
        id: Long,
        favorite: Boolean
    )

    @Query(
        """
SELECT *
FROM stories
WHERE isFavorite = 1
ORDER BY createdAt DESC
"""
    )
    fun getFavoriteStories(): Flow<List<StoryEntity>>


    @Query(
        """
    UPDATE stories
    SET lastReadSentence = :sentence
    WHERE id = :storyId
"""
    )
    suspend fun updateReadingPosition(
        storyId: Long,
        sentence: Int
    )

    @Query(
        """
    SELECT * FROM stories
    WHERE category = :category
    ORDER BY id DESC
    """
    )
    fun observeStoriesByCategory(
        category: String
    ): Flow<List<StoryEntity>>
}