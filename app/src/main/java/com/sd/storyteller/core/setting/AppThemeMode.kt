package com.sd.storyteller.core.setting

enum class AppThemeMode {
    SYSTEM, LIGHT, DARK;

    companion object {
        fun from(value: String?): AppThemeMode =
            entries.firstOrNull { it.name == value } ?: SYSTEM
    }
}
