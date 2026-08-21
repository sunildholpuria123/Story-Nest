package com.sd.storyteller.domain.usecase

/**
 * Created by SDHOLPURIA on 07-08-2026.
 */

import com.sd.storyteller.domain.repository.StoryRepository
import javax.inject.Inject

class UpdateReadingPositionUseCase @Inject constructor(
    private val repository: StoryRepository
) {

    suspend operator fun invoke(
        storyId: Long,
        sentence: Int
    ) {

        repository.updateReadingPosition(
            storyId = storyId,
            sentence = sentence
        )
    }
}
