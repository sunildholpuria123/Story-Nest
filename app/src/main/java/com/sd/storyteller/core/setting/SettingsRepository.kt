package com.sd.storyteller.core.setting

import android.content.Context
import com.sd.storyteller.core.constants.StoryLanguage
import com.sd.storyteller.ui.theme.StoryTheme
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by SDHOLPURIA on 08-08-2026.
 */

@Singleton
class SettingsRepository @Inject constructor(
    @ApplicationContext
    private val context: Context
) {

    private val preferences =
        context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )

    // =========================================================
    // Read Settings
    // =========================================================

    fun getSettings(): AppSettings {

        return AppSettings(

            language =
                getLanguage(),

            readAloudEnabled =
                preferences.getBoolean(
                    KEY_READ_ALOUD,
                    DEFAULT_READ_ALOUD
                ),

            musicEnabled =
                preferences.getBoolean(
                    KEY_MUSIC,
                    DEFAULT_MUSIC
                ),

            readerTheme =
                getReaderTheme()
        )
    }

    // =========================================================
    // Language
    // =========================================================

    fun getLanguage(): StoryLanguage {

        val code =
            preferences.getString(
                KEY_LANGUAGE,
                StoryLanguage.HINDI.code
            )

        return StoryLanguage.entries
            .firstOrNull {
                it.code == code
            }
            ?: StoryLanguage.HINDI
    }

    fun setLanguage(
        language: StoryLanguage
    ) {

        preferences.edit()
            .putString(
                KEY_LANGUAGE,
                language.code
            )
            .apply()
    }

    // =========================================================
    // Read Aloud
    // =========================================================

    fun isReadAloudEnabled(): Boolean {

        return preferences.getBoolean(
            KEY_READ_ALOUD,
            DEFAULT_READ_ALOUD
        )
    }

    fun setReadAloudEnabled(
        enabled: Boolean
    ) {

        preferences.edit()
            .putBoolean(
                KEY_READ_ALOUD,
                enabled
            )
            .apply()
    }

    // =========================================================
    // Music
    // =========================================================

    fun isMusicEnabled(): Boolean {

        return preferences.getBoolean(
            KEY_MUSIC,
            DEFAULT_MUSIC
        )
    }

    fun setMusicEnabled(
        enabled: Boolean
    ) {

        preferences.edit()
            .putBoolean(
                KEY_MUSIC,
                enabled
            )
            .apply()
    }

    // =========================================================
    // Reader Theme
    // =========================================================

    fun getReaderTheme(): StoryTheme {

        val value =
            preferences.getString(
                KEY_READER_THEME,
                StoryTheme.BEDTIME.name
            )

        return StoryTheme.entries
            .firstOrNull {
                it.name == value
            }
            ?: StoryTheme.BEDTIME
    }

    fun setReaderTheme(
        theme: StoryTheme
    ) {

        preferences.edit()
            .putString(
                KEY_READER_THEME,
                theme.name
            )
            .apply()
    }

    // =========================================================
// Story Generation Limit
// =========================================================

    fun getStoryGenerationAttemptsToday(): Int {

        return preferences.getInt(
            KEY_STORY_GENERATION_ATTEMPTS,
            0
        )
    }

    fun getStoryGenerationDate(): String? {

        return preferences.getString(
            KEY_STORY_GENERATION_DATE,
            null
        )
    }

    fun incrementStoryGenerationAttempt() {

        val today =
            java.text.SimpleDateFormat(
                "yyyy-MM-dd",
                java.util.Locale.US
            ).format(
                java.util.Date()
            )

        val savedDate =
            getStoryGenerationDate()

        val attempts =
            if (savedDate == today) {
                getStoryGenerationAttemptsToday()
            } else {
                0
            }

        preferences.edit()
            .putString(
                KEY_STORY_GENERATION_DATE,
                today
            )
            .putInt(
                KEY_STORY_GENERATION_ATTEMPTS,
                attempts + 1
            )
            .apply()
    }

    fun getRemainingStoryAttempts(): Int {

        val today =
            java.text.SimpleDateFormat(
                "yyyy-MM-dd",
                java.util.Locale.US
            ).format(
                java.util.Date()
            )

        val savedDate =
            getStoryGenerationDate()

        if (savedDate != today) {
            return MAX_STORY_GENERATION_ATTEMPTS
        }

        return (
                MAX_STORY_GENERATION_ATTEMPTS -
                        getStoryGenerationAttemptsToday()
                ).coerceAtLeast(0)
    }

    companion object {

        private const val PREFS_NAME =
            "storynest_settings"

        private const val KEY_LANGUAGE =
            "story_language"

        private const val KEY_READ_ALOUD =
            "read_aloud_enabled"

        private const val KEY_MUSIC =
            "music_enabled"

        private const val KEY_READER_THEME =
            "reader_theme"

        private const val DEFAULT_READ_ALOUD =
            true

        private const val DEFAULT_MUSIC =
            false

        private const val KEY_STORY_GENERATION_ATTEMPTS =
            "story_generation_attempts"

        private const val KEY_STORY_GENERATION_DATE =
            "story_generation_date"

        const val MAX_STORY_GENERATION_ATTEMPTS =
            3
    }
}