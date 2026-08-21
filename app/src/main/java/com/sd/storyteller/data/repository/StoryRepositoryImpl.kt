package com.sd.storyteller.data.repository

import com.sd.storyteller.BuildConfig
import com.sd.storyteller.core.util.GeminiStoryParser
import com.sd.storyteller.data.local.dao.StoryDao
import com.sd.storyteller.data.local.entity.StoryEntity
import com.sd.storyteller.data.remote.api.GeminiApi
import com.sd.storyteller.data.remote.model.Content
import com.sd.storyteller.data.remote.model.GeminiRequest
import com.sd.storyteller.data.remote.model.Part
import com.sd.storyteller.domain.model.Story
import com.sd.storyteller.domain.model.StoryRequest
import com.sd.storyteller.domain.repository.StoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by SDHOLPURIA on 01-08-2026.
 */
class StoryRepositoryImpl @Inject constructor(
    private val api: GeminiApi,
    private val dao: StoryDao
) : StoryRepository {

    override fun getStories(): Flow<List<Story>> {
        return dao.getStories().map { list ->
            list.map { entity ->
                entity.toDomain()
            }
        }
    }

    override suspend fun getStory(id: Long): Story? {
        return dao.getStory(id)?.toDomain()
    }

    // ---------------------------------------------------------
    // Gemini Prompt
    // ---------------------------------------------------------

    private fun buildPrompt(
        request: StoryRequest
    ): String {

        val topicName =
            request.topic?.name
                ?.takeIf { it.isNotBlank() }
                ?: "No specific topic selected"

        return """
            Create an original children's story.

            Language:
            ${request.language.displayName}

            Language Code:
            ${request.language.code}

            Character Name:
            ${request.characterName}

            Character Age:
            ${request.age}

            Category:
            ${request.category.name}

            Specific Story Topic:
            $topicName

            Mood:
            ${request.mood.title}

            Length:
            ${request.length.title}

            Requirements:

            1. Write the entire story in ${request.language.displayName}.
            2. Use the natural writing system of that language.
            3. Do not mix languages unnecessarily.
            4. Make the story suitable for a ${request.age}-year-old child.
            5. Make the story engaging, imaginative, and age appropriate.
            6. Follow the selected category.
            7. If a specific story topic is provided, make the topic
               the central subject of the story.
            8. Follow the selected mood.
            9. Give the story a meaningful and positive ending.
            10. If the topic is historical, mythological, religious,
                or based on traditional Indian stories, keep the story
                respectful and suitable for children.
            11. Do not present fictional events as established historical
                facts.
            12. If the story is inspired by mythology or traditional
                stories, keep the presentation child-friendly.
            13. Generate a short title in ${request.language.displayName}.
            14. Do not include markdown.
            15. Return only the title followed by the story.

        """.trimIndent()
    }

    // ---------------------------------------------------------
    // Delete
    // ---------------------------------------------------------

    override suspend fun deleteStory(
        id: Long
    ) {
        dao.delete(id)
    }

    // ---------------------------------------------------------
    // Favorites
    // ---------------------------------------------------------

    override fun getFavoriteStories(): Flow<List<Story>> {

        return dao
            .getFavoriteStories()
            .map { list ->
                list.map {
                    it.toDomain()
                }
            }
    }

    override suspend fun updateFavorite(
        id: Long,
        favorite: Boolean
    ) {

        dao.updateFavorite(
            id,
            favorite
        )
    }

    // ---------------------------------------------------------
    // Reading Position
    // ---------------------------------------------------------

    override suspend fun updateReadingPosition(
        storyId: Long,
        sentence: Int
    ) {

        dao.updateReadingPosition(
            storyId = storyId,
            sentence = sentence
        )
    }

    // ---------------------------------------------------------
    // Stories By Category
    // ---------------------------------------------------------

    override fun observeStoriesByCategory(
        category: String
    ): Flow<List<Story>> {

        return dao
            .observeStoriesByCategory(category)
            .map { entities ->

                entities.map {
                    it.toDomain()
                }
            }
    }

    // ---------------------------------------------------------
    // Generate Story
    // ---------------------------------------------------------

    override suspend fun generateStory(
        request: StoryRequest
    ): Result<Story> {

        return try {

            // ---------------------------------------------
            // Build Prompt
            // ---------------------------------------------

            val prompt =
                buildPrompt(request)

            // ---------------------------------------------
            // Gemini API
            // ---------------------------------------------

            val response =
                api.generateStory(

                    apiKey =
                        BuildConfig.GEMINI_API_KEY,

                    request =
                        GeminiRequest(
                            contents =
                                listOf(
                                    Content(
                                        parts =
                                            listOf(
                                                Part(prompt)
                                            )
                                    )
                                )
                        )
                )

            // ---------------------------------------------
            // Extract Gemini Response
            // ---------------------------------------------

            val storyText =
                response.candidates
                    .firstOrNull()
                    ?.content
                    ?.parts
                    ?.firstOrNull()
                    ?.text
                    ?.trim()

            if (storyText.isNullOrBlank()) {

                Result.failure(
                    IllegalStateException(
                        "Empty response from Gemini"
                    )
                )

            } else {

                // -----------------------------------------
                // Parse Gemini Response
                // -----------------------------------------

                val parsedStory =
                    GeminiStoryParser.parse(
                        storyText,
                        request.language
                    )

                if (parsedStory.content.isBlank()) {

                    Result.failure(
                        IllegalStateException(
                            "Gemini returned an empty story"
                        )
                    )

                } else {

                    // -------------------------------------
                    // Domain Story
                    // -------------------------------------

                    val story =
                        Story(
                            title =
                                parsedStory.title,

                            content =
                                parsedStory.content,

                            category =
                                request.category.id,

                            language =
                                request.language,
                            topic = request.topic
                        )

                    // -------------------------------------
                    // Save Story
                    // -------------------------------------

                    dao.insert(

                        StoryEntity(

                            title =
                                story.title,

                            content =
                                story.content,

                            characterName =
                                request.characterName,

                            category =
                                request.category.id,

                            // NEW
                            topic =
                                request.topic,

                            mood =
                                request.mood.title,

                            createdAt =
                                System.currentTimeMillis(),

                            isFavorite =
                                false,

                            language =
                                request.language.code
                        )
                    )

                    Result.success(
                        story
                    )
                }
            }

        } catch (e: Exception) {

            Result.failure(e)
        }
    }
}