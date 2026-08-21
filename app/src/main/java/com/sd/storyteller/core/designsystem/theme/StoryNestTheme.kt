package com.sd.storyteller.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

/**
 * Created by SDHOLPURIA on 31-07-2026.
 */
private val DarkColorScheme = darkColorScheme(
    primary = StoryNestPalette.Primary,
    secondary = StoryNestPalette.Secondary,
    tertiary = StoryNestPalette.Accent,

    background = StoryNestPalette.Background,
    surface = StoryNestPalette.Surface,

    onPrimary = StoryNestPalette.TextPrimary,
    onSecondary = StoryNestPalette.TextPrimary,
    onTertiary = StoryNestPalette.TextPrimary,

    onBackground = StoryNestPalette.TextPrimary,
    onSurface = StoryNestPalette.TextPrimary,

    error = StoryNestPalette.Error
)

private val LightColorScheme = lightColorScheme(
    primary = StoryNestPalette.Primary,
    secondary = StoryNestPalette.Secondary,
    tertiary = StoryNestPalette.Accent,

    background = StoryNestPalette.TextPrimary,
    surface = StoryNestPalette.TextPrimary,

    onPrimary = StoryNestPalette.TextPrimary,
    onSecondary = StoryNestPalette.TextPrimary,
    onTertiary = StoryNestPalette.TextPrimary,

    onBackground = StoryNestPalette.Background,
    onSurface = StoryNestPalette.Background,

    error = StoryNestPalette.Error
)

@Composable
fun StoryNestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = if (darkTheme) {
            DarkColorScheme
        } else {
            LightColorScheme
        },
        typography = StoryNestTypography,
        shapes = StoryNestShapes,
        content = content
    )
}