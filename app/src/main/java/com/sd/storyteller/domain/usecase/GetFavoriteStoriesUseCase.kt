package com.sd.storyteller.domain.usecase

import com.sd.storyteller.domain.repository.StoryRepository
import javax.inject.Inject

/**
 * Created by SDHOLPURIA on 05-08-2026.
 */
class GetFavoriteStoriesUseCase @Inject constructor(
    private val repository: StoryRepository
) {

    operator fun invoke() =
        repository.getFavoriteStories()
}