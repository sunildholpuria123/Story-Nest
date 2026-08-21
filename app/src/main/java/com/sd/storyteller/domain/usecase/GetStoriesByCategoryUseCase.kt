package com.sd.storyteller.domain.usecase

import com.sd.storyteller.domain.model.Story
import com.sd.storyteller.domain.repository.StoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by SDHOLPURIA on 07-08-2026.
 */
class GetStoriesByCategoryUseCase @Inject constructor(
    private val repository: StoryRepository
) {

    operator fun invoke(
        category: String
    ): Flow<List<Story>> {

        return repository
            .observeStoriesByCategory(category)
    }
}