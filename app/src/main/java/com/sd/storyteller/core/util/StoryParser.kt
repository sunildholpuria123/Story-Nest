package com.sd.storyteller.core.util

import com.sd.storyteller.core.constants.StoryLanguage

/**
 * Created by SDHOLPURIA on 06-08-2026.
 */

object StoryParser {

    /**
     * Splits story content into sentences based on
     * the selected story language.
     */
    fun splitIntoSentences(
        story: String,
        language: StoryLanguage
    ): List<String> {

        if (story.isBlank()) {
            return emptyList()
        }

        val regex = when (language) {

            StoryLanguage.HINDI -> {
                Regex("(?<=[।!?！？])\\s+")
            }

            StoryLanguage.ENGLISH -> {
                Regex("(?<=[.!?！？])\\s+")
            }

            StoryLanguage.SPANISH -> {
                Regex("(?<=[.!?¿¡！？])\\s+")
            }

            StoryLanguage.FRENCH -> {
                Regex("(?<=[.!?！？])\\s+")
            }
        }

        return story
            .trim()
            .split(regex)
            .map {
                it.trim()
            }
            .filter {
                it.isNotBlank()
            }
    }
}