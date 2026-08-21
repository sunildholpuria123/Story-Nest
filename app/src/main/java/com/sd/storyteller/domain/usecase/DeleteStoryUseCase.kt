package com.sd.storyteller.domain.usecase

import com.sd.storyteller.domain.repository.StoryRepository
import javax.inject.Inject

/**
 * Created by SDHOLPURIA on 05-08-2026.
 */
class DeleteStoryUseCase @Inject constructor(
    private val repository: StoryRepository
) {

    suspend operator fun invoke(
        id: Long
    ) {

        repository.deleteStory(id)
    }
}