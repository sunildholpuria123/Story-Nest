package com.sd.storyteller.core.tts

import com.sd.storyteller.ui.theme.StoryTheme

/**
 * Maps the Reader theme/category to a storytelling
 * voice style.
 *
 * Created by SDHOLPURIA on 08-08-2026.
 */
object SpeechStyleMapper {

    fun fromTheme(
        theme: StoryTheme
    ): SpeechStyle {

        return when (theme) {

            // -------------------------------------------------
            // Jungle
            // Energetic / adventurous
            // -------------------------------------------------

            StoryTheme.JUNGLE ->
                SpeechStyle(
                    speechRate = 1.08f,
                    pitch = 1.05f
                )

            // -------------------------------------------------
            // Ocean
            // Calm / flowing
            // -------------------------------------------------

            StoryTheme.OCEAN ->
                SpeechStyle(
                    speechRate = 0.88f,
                    pitch = 0.95f
                )

            // -------------------------------------------------
            // Space
            // Slow / mysterious
            // -------------------------------------------------

            StoryTheme.SPACE ->
                SpeechStyle(
                    speechRate = 0.82f,
                    pitch = 0.90f
                )

            // -------------------------------------------------
            // Princess
            // Gentle / warm
            // -------------------------------------------------

            StoryTheme.PRINCESS ->
                SpeechStyle(
                    speechRate = 0.90f,
                    pitch = 1.08f
                )

            // -------------------------------------------------
            // Magic
            // Wonder / dreamy
            // -------------------------------------------------

            StoryTheme.MAGIC ->
                SpeechStyle(
                    speechRate = 0.86f,
                    pitch = 1.05f
                )

            // -------------------------------------------------
            // Adventure
            // Exciting / energetic
            // -------------------------------------------------

            StoryTheme.ADVENTURE ->
                SpeechStyle(
                    speechRate = 1.10f,
                    pitch = 1.05f
                )

            // -------------------------------------------------
            // Animals
            // Playful
            // -------------------------------------------------

            StoryTheme.ANIMALS ->
                SpeechStyle(
                    speechRate = 1.05f,
                    pitch = 1.12f
                )

            // -------------------------------------------------
            // Bedtime
            // Soft / soothing
            // -------------------------------------------------

            StoryTheme.BEDTIME ->
                SpeechStyle(
                    speechRate = 0.75f,
                    pitch = 0.90f
                )
        }
    }
}