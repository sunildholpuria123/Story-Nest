package com.sd.storyteller.core.constants

/**
 * Created by SDHOLPURIA on 07-08-2026.
 */
enum class StoryLanguage(
    val code: String,
    val displayName: String,
    val emoji: String
) {

    HINDI(
        code = "hi-IN",
        displayName = "हिन्दी",
        emoji = "🇮🇳"
    ),

    ENGLISH(
        code = "en-US",
        displayName = "English",
        emoji = "🇬🇧"
    ),

    SPANISH(
        code = "es-ES",
        displayName = "Español",
        emoji = "🇪🇸"
    ),

    FRENCH(
        code = "fr-FR",
        displayName = "Français",
        emoji = "🇫🇷"
    )
}