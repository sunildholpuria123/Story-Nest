package com.sd.storyteller.domain.usecase

import com.sd.storyteller.domain.model.Story
import com.sd.storyteller.domain.model.StoryRequest
import com.sd.storyteller.domain.repository.StoryRepository

/**
 * Created by SDHOLPURIA on 01-08-2026.
 */

class GenerateStoryUseCase(
    private val repository: StoryRepository
) {

    suspend operator fun invoke(
        request: StoryRequest
    ): Result<Story> {

        return repository.generateStory(request)
    }
}