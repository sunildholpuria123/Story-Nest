package com.sd.storyteller.core.constants

import com.sd.storyteller.feature.create.model.StoryTopic

object StoryTopics {

    val all: List<StoryTopic> = listOf(

        // -------------------------------------------------
        // Indian Wisdom & Folk Tales
        // -------------------------------------------------

        StoryTopic(
            id = "akbar_birbal",
            name = "Akbar Birbal",
            emoji = "👑",
            description = "Clever and humorous stories of Akbar and Birbal."
        ),

        StoryTopic(
            id = "tenali_raman",
            name = "Tenali Raman",
            emoji = "🧠",
            description = "Funny and clever stories of Tenali Raman."
        ),

        StoryTopic(
            id = "panchatantra",
            name = "Panchatantra",
            emoji = "🐾",
            description = "Animal stories with wisdom and moral lessons."
        ),

        StoryTopic(
            id = "vikram_betal",
            name = "Vikram Betal",
            emoji = "👻",
            description = "Mystery and wisdom stories of King Vikram and Betal."
        ),

        // -------------------------------------------------
        // Indian History
        // -------------------------------------------------

        StoryTopic(
            id = "indian_history",
            name = "Indian History",
            emoji = "🏛️",
            description = "Interesting stories inspired by Indian history."
        ),

        StoryTopic(
            id = "indian_kings",
            name = "Indian Kings",
            emoji = "👑",
            description = "Stories about famous Indian kings and rulers."
        ),

        StoryTopic(
            id = "freedom_fighters",
            name = "Freedom Fighters",
            emoji = "🇮🇳",
            description = "Child-friendly stories about India's freedom fighters."
        ),

        // -------------------------------------------------
        // Indian Gods & Epics
        // -------------------------------------------------

        StoryTopic(
            id = "indian_gods",
            name = "Indian Gods",
            emoji = "🙏",
            description = "Child-friendly stories inspired by Indian mythology."
        ),

        StoryTopic(
            id = "krishna",
            name = "Lord Krishna",
            emoji = "🦚",
            description = "Stories from the life and teachings of Krishna."
        ),

        StoryTopic(
            id = "rama",
            name = "Lord Rama",
            emoji = "🏹",
            description = "Child-friendly stories inspired by Lord Rama."
        ),

        StoryTopic(
            id = "hanuman",
            name = "Lord Hanuman",
            emoji = "🚩",
            description = "Stories about courage, devotion and Hanuman."
        ),

        StoryTopic(
            id = "ramayana",
            name = "Ramayana",
            emoji = "🏹",
            description = "Child-friendly stories inspired by the Ramayana."
        ),

        StoryTopic(
            id = "mahabharata",
            name = "Mahabharata",
            emoji = "⚔️",
            description = "Child-friendly stories inspired by the Mahabharata."
        ),

        // -------------------------------------------------
        // General
        // -------------------------------------------------

        StoryTopic(
            id = "moral_stories",
            name = "Moral Stories",
            emoji = "💡",
            description = "Stories that teach meaningful life lessons."
        ),

        StoryTopic(
            id = "fairy_tales",
            name = "Fairy Tales",
            emoji = "🧚",
            description = "Magical and imaginative fairy tales."
        ),

        StoryTopic(
            id = "animal_stories",
            name = "Animal Stories",
            emoji = "🦁",
            description = "Fun stories featuring animals."
        )
    )

    fun findById(
        id: String
    ): StoryTopic? {

        return all.firstOrNull {
            it.id.equals(
                id,
                ignoreCase = true
            )
        }
    }
}