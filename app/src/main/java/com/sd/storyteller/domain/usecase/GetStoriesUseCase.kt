package com.sd.storyteller.domain.usecase

/**
 * Created by SDHOLPURIA on 01-08-2026.
 */

import com.sd.storyteller.domain.model.Story
import com.sd.storyteller.domain.repository.StoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/*
class GetStoriesUseCase @Inject constructor(
    private val repository: StoryRepository
) {

    operator fun invoke() =
        repository.getStories()
}*/

class GetStoriesUseCase @Inject constructor(
    private val repository: StoryRepository
) {

    operator fun invoke(
        category: String? = null
    ): Flow<List<Story>> {

        return repository
            .getStories()
            .map { stories ->

                if (category.isNullOrBlank()) {
                    stories
                } else {
                    stories.filter {
                        it.category.equals(
                            category,
                            ignoreCase = true
                        )
                    }
                }
            }
    }
}