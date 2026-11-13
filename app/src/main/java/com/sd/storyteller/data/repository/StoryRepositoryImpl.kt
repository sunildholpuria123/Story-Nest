package com.sd.storyteller.data.repository

import android.util.Log
import com.sd.storyteller.BuildConfig
import com.sd.storyteller.core.util.GeminiStoryParser
import com.sd.storyteller.data.local.dao.StoryDao
import com.sd.storyteller.data.local.entity.StoryEntity
import com.sd.storyteller.data.local.secure.SecureTokenStorage
import com.sd.storyteller.data.remote.api.GeminiApi
import com.sd.storyteller.data.remote.api.SupabaseStoryApi
import com.sd.storyteller.data.remote.model.StoryNestGenerateRequest
import com.sd.storyteller.data.remote.model.story.SupabaseStoryDto
import com.sd.storyteller.data.repository.SupabaseAuthRepository
import com.sd.storyteller.domain.model.Story
import com.sd.storyteller.domain.model.StoryRequest
import com.sd.storyteller.domain.repository.StoryRepository
import com.sd.storyteller.feature.create.model.StoryTopic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import javax.inject.Inject

/**
 * Created by SDHOLPURIA on 01-08-2026.
 */
class StoryRepositoryImpl @Inject constructor(
    private val api: GeminiApi,
    private val dao: StoryDao,
    private val supabaseStoryApi: SupabaseStoryApi,
    private val tokenStorage: SecureTokenStorage,
    private val authRepository: SupabaseAuthRepository
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

    override suspend fun deleteStory(id: Long) {
        dao.softDelete(id)
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

    /**
     * Calls the Worker with the saved access token. If Supabase rejects that
     * token as expired/invalid, refresh the session once and retry once.
     * Quota errors (429) and backend errors are not retried.
     */
    private suspend fun generateWithTokenRefresh(
        prompt: String
    ): com.sd.storyteller.data.remote.model.GeminiResponse {
        val accessToken = tokenStorage.getAccessToken()
            ?: error("Please login to generate stories.")

        return try {
            api.generateStory(
                authorization = "Bearer $accessToken",
                request = StoryNestGenerateRequest(prompt = prompt)
            )
        } catch (error: HttpException) {
            if (error.code() != 401 && error.code() != 403) {
                throw error
            }

            val refreshedToken = authRepository.refreshAccessToken()
                .getOrElse { refreshError -> throw refreshError }

            api.generateStory(
                authorization = "Bearer $refreshedToken",
                request = StoryNestGenerateRequest(prompt = prompt)
            )
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
            val response = generateWithTokenRefresh(prompt)

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

                    val generatedStoryId = dao.insert(
                        StoryEntity(
                            title = story.title,
                            content = story.content,
                            characterName = request.characterName,
                            category = request.category.id,
                            topic = request.topic,
                            mood = request.mood.title,
                            createdAt = System.currentTimeMillis(),
                            isFavorite = false,
                            language = request.language.code
                        )
                    )

// Return the story with its actual Room database ID.
                    Result.success(
                        story.copy(id = generatedStoryId)
                    )
                }
            }

        } catch (e: Exception) {

            if (e is HttpException) {
                val errorBody = e.response()
                    ?.errorBody()
                    ?.string()

                Log.e(
                    "StoryNestGeneration",
                    "HTTP Error: ${e.code()}, Body: $errorBody",
                    e
                )
            } else {
                Log.e(
                    "StoryNestGeneration",
                    "Story generation failed",
                    e
                )
            }

            Result.failure(e)
        }
    }

    suspend fun uploadStoriesToSupabase(): Result<Int> {
        return try {
            val userId = tokenStorage.getUserId()
                ?: return Result.failure(
                    IllegalStateException("User is not logged in")
                )

            val accessToken = tokenStorage.getAccessToken()
                ?: return Result.failure(
                    IllegalStateException("Access token not found")
                )

            val localStories = dao.getAllStories()

            val cloudResponse = supabaseStoryApi.getUserStories(
                apiKey = BuildConfig.SUPABASE_PUBLISHABLE_KEY,
                authorization = "Bearer $accessToken",
                userIdFilter = "eq.$userId"
            )

            if (!cloudResponse.isSuccessful) {
                return Result.failure(
                    Exception("Cloud fetch failed: ${cloudResponse.code()}")
                )
            }

            val cloudMap = cloudResponse.body().orEmpty()
                .associateBy { it.id }

            val storiesToUpload = localStories.filter { local ->
                val cloud = cloudMap[local.cloudId]
                cloud == null || local.updatedAt > cloud.updatedAt
            }

            if (storiesToUpload.isEmpty()) {
                return Result.success(0)
            }

            val dtoList = storiesToUpload.map { story ->
                SupabaseStoryDto(
                    id = story.cloudId,
                    userId = userId,
                    title = story.title,
                    content = story.content,
                    characterName = story.characterName,
                    category = story.category,
                    mood = story.mood,
                    topic = story.topic?.let {
                        Json.encodeToString(it)
                    },
                    language = story.language,
                    isFavorite = story.isFavorite,
                    createdAt = story.createdAt,
                    lastReadSentence = story.lastReadSentence,
                    updatedAt = story.updatedAt,
                    isDeleted = story.isDeleted
                )
            }

            val response = supabaseStoryApi.upsertStories(
                apiKey = BuildConfig.SUPABASE_PUBLISHABLE_KEY,
                authorization = "Bearer $accessToken",
                stories = dtoList
            )

            if (response.isSuccessful) {
                Result.success(dtoList.size)
            } else {
                Result.failure(
                    Exception("Upload failed: ${response.code()}")
                )
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun downloadStoriesFromSupabase(): Result<Int> {
        return try {
            val userId = tokenStorage.getUserId()
                ?: return Result.failure(
                    IllegalStateException("User is not logged in")
                )

            val accessToken = tokenStorage.getAccessToken()
                ?: return Result.failure(
                    IllegalStateException("Access token not found")
                )

            val response = supabaseStoryApi.getUserStories(
                apiKey = BuildConfig.SUPABASE_PUBLISHABLE_KEY,
                authorization = "Bearer $accessToken",
                userIdFilter = "eq.$userId"
            )

            if (!response.isSuccessful) {
                return Result.failure(
                    Exception("Download failed: ${response.code()}")
                )
            }

            val cloudStories = response.body().orEmpty()
            var updatedCount = 0

            cloudStories.forEach { cloudStory ->

                val existingStory =
                    dao.getStoryByCloudId(cloudStory.id)

                // Only download if cloud version is newer
                if (
                    existingStory == null ||
                    cloudStory.updatedAt > existingStory.updatedAt
                ) {
                    val topic = cloudStory.topic?.let {
                        runCatching {
                            Json.decodeFromString<StoryTopic>(it)
                        }.getOrNull()
                    }

                    val localStory = StoryEntity(
                        id = existingStory?.id ?: 0L,
                        cloudId = cloudStory.id,
                        title = cloudStory.title,
                        content = cloudStory.content,
                        characterName = cloudStory.characterName,
                        category = cloudStory.category,
                        mood = cloudStory.mood,
                        topic = topic,
                        language = cloudStory.language,
                        isFavorite = cloudStory.isFavorite,
                        createdAt = cloudStory.createdAt,
                        lastReadSentence = cloudStory.lastReadSentence,
                        updatedAt = cloudStory.updatedAt,
                        isDeleted = cloudStory.isDeleted
                    )

                    dao.upsertStory(localStory)
                    updatedCount++
                }
            }

            Result.success(updatedCount)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun syncStories(): Result<String> {
        return try {
            val uploadResult = uploadStoriesToSupabase()

            if (uploadResult.isFailure) {
                return Result.failure(
                    uploadResult.exceptionOrNull()
                        ?: Exception("Upload failed")
                )
            }

            val downloadResult = downloadStoriesFromSupabase()

            if (downloadResult.isFailure) {
                return Result.failure(
                    downloadResult.exceptionOrNull()
                        ?: Exception("Download failed")
                )
            }

            val uploaded = uploadResult.getOrThrow()
            val downloaded = downloadResult.getOrThrow()

            Result.success(
                "Sync completed. Uploaded: $uploaded, Downloaded: $downloaded"
            )

        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}