package com.sd.storyteller.core.util

import com.sd.storyteller.core.constants.StoryLanguage

/**
 * Parses Gemini story responses according to the
 * selected story language.
 */
object GeminiStoryParser {

    data class ParsedStory(
        val title: String,
        val content: String
    )

    fun parse(
        response: String,
        language: StoryLanguage
    ): ParsedStory {

        val cleaned =
            cleanResponse(response)

        if (cleaned.isBlank()) {
            return ParsedStory(
                title = defaultTitle(language),
                content = ""
            )
        }

        val lines =
            cleaned
                .lines()
                .map { it.trim() }
                .filter { it.isNotBlank() }

        if (lines.isEmpty()) {
            return ParsedStory(
                title = defaultTitle(language),
                content = ""
            )
        }

        val title =
            extractTitle(
                lines = lines,
                language = language
            )

        val content =
            extractContent(
                lines = lines,
                language = language
            )

        return ParsedStory(
            title = title.ifBlank {
                defaultTitle(language)
            },
            content = content
        )
    }

    // ---------------------------------------------------------
    // Clean response
    // ---------------------------------------------------------

    private fun cleanResponse(
        response: String
    ): String {

        return response
            .trim()
            .removePrefix("```text")
            .removePrefix("```")
            .removeSuffix("```")
            .trim()
    }

    // ---------------------------------------------------------
    // Title
    // ---------------------------------------------------------

    private fun extractTitle(
        lines: List<String>,
        language: StoryLanguage
    ): String {

        val firstLine =
            lines.first()

        return removeTitleLabel(
            firstLine,
            language
        )
            .removePrefix("#")
            .removePrefix("*")
            .removeSuffix("*")
            .trim()
    }

    // ---------------------------------------------------------
    // Content
    // ---------------------------------------------------------

    private fun extractContent(
        lines: List<String>,
        language: StoryLanguage
    ): String {

        val startIndex =
            if (isStoryLabel(
                    lines.getOrNull(1),
                    language
                )
            ) {
                2
            } else {
                1
            }

        return lines
            .drop(startIndex)
            .joinToString("\n")
            .trim()
    }

    // ---------------------------------------------------------
    // Title labels
    // ---------------------------------------------------------

    private fun removeTitleLabel(
        value: String,
        language: StoryLanguage
    ): String {

        val labels =
            when (language) {

                StoryLanguage.ENGLISH -> listOf(
                    "Title:",
                    "title:",
                    "Story Title:"
                )

                StoryLanguage.HINDI -> listOf(
                    "शीर्षक:",
                    "कहानी का शीर्षक:"
                )

                else -> listOf(
                    "Title:",
                    "title:",
                    "Story Title:"
                )
            }

        return labels
            .firstOrNull {
                value.startsWith(
                    it,
                    ignoreCase = true
                )
            }
            ?.let {
                value.substring(
                    it.length
                ).trim()
            }
            ?: value
    }

    // ---------------------------------------------------------
    // Story labels
    // ---------------------------------------------------------

    private fun isStoryLabel(
        value: String?,
        language: StoryLanguage
    ): Boolean {

        if (value.isNullOrBlank()) {
            return false
        }

        val labels =
            when (language) {

                StoryLanguage.ENGLISH -> listOf(
                    "Story:",
                    "story:",
                    "Story Text:",
                    "Content:"
                )

                StoryLanguage.HINDI -> listOf(
                    "कहानी:",
                    "कहानी की शुरुआत:",
                    "कहानी का विवरण:"
                )

                else -> listOf(
                    "Story:",
                    "story:",
                    "Content:"
                )
            }

        return labels.any {
            value.startsWith(
                it,
                ignoreCase = true
            )
        }
    }

    // ---------------------------------------------------------
    // Default title
    // ---------------------------------------------------------

    private fun defaultTitle(
        language: StoryLanguage
    ): String {

        return when (language) {

            StoryLanguage.ENGLISH ->
                "My Story"

            StoryLanguage.HINDI ->
                "मेरी कहानी"

            else ->
                "My Story"
        }
    }
}