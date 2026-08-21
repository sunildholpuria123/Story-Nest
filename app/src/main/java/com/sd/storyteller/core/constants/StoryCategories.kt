package com.sd.storyteller.core.constants

import com.sd.storyteller.feature.create.model.StoryCategory


object StoryCategories {

    val all: List<StoryCategory> = listOf(

        StoryCategory(
            id = "adventure",
            name = "Adventure",
            emoji = "🗺️",
            description = "Exciting journeys and discoveries."
        ),

        StoryCategory(
            id = "fantasy",
            name = "Fantasy",
            emoji = "🧙",
            description = "Magical worlds and imagination."
        ),

        StoryCategory(
            id = "mystery",
            name = "Mystery",
            emoji = "🔎",
            description = "Secrets, clues and mysteries."
        ),

        StoryCategory(
            id = "fairy_tales",
            name = "Fairy Tales",
            emoji = "🧚",
            description = "Classic magical stories."
        ),

        StoryCategory(
            id = "animals",
            name = "Animals",
            emoji = "🦁",
            description = "Stories featuring wonderful animals."
        ),

        StoryCategory(
            id = "bedtime",
            name = "Bedtime",
            emoji = "🌙",
            description = "Calm stories for bedtime."
        ),

        StoryCategory(
            id = "friendship",
            name = "Friendship",
            emoji = "🤝",
            description = "Stories about friends."
        ),

        StoryCategory(
            id = "moral",
            name = "Moral",
            emoji = "💡",
            description = "Stories with meaningful lessons."
        )
    )

    fun findById(
        id: String
    ): StoryCategory? {
        return all.firstOrNull {
            it.id.equals(
                id,
                ignoreCase = true
            )
        }
    }
}