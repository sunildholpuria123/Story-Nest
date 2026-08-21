package com.sd.storyteller.ui.theme

/**
 * Created by SDHOLPURIA on 06-08-2026.
 */

object StoryThemeMapper {

    fun fromCategory(
        category: String
    ): StoryTheme {

        return when (category.lowercase()) {

            "bedtime" ->
                StoryTheme.BEDTIME

            "jungle" ->
                StoryTheme.JUNGLE

            "ocean" ->
                StoryTheme.OCEAN

            "space" ->
                StoryTheme.SPACE

            "princess" ->
                StoryTheme.PRINCESS

            "fantasy" ->
                StoryTheme.MAGIC

            "adventure" ->
                StoryTheme.ADVENTURE

            "animals" ->
                StoryTheme.ANIMALS

            else ->
                StoryTheme.BEDTIME
        }
    }
}
