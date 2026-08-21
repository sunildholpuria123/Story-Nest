package com.sd.storyteller.domain.usecase

/**
 * Created by SDHOLPURIA on 05-08-2026.
 */

import com.sd.storyteller.domain.repository.StoryRepository
import javax.inject.Inject

class GetStoryUseCase @Inject constructor(
    private val repository: StoryRepository
) {

    suspend operator fun invoke(
        id: Long
    ) = repository.getStory(id)
}