package com.sd.storyteller.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

private val LocalStoryNestColors = compositionLocalOf { StoryNestDarkPalette }

/**
 * Current semantic StoryNest colors. Read this only from composable UI.
 * This keeps every screen and reusable component in sync with the selected theme.
 */
val StoryNestPalette: StoryNestColors
    @Composable get() = LocalStoryNestColors.current

private fun StoryNestColors.toDarkColorScheme() = darkColorScheme(
    primary = Primary,
    onPrimary = ColorTokens.White,
    primaryContainer = Card,
    onPrimaryContainer = TextPrimary,
    secondary = Secondary,
    onSecondary = Background,
    tertiary = Accent,
    onTertiary = Background,
    background = Background,
    onBackground = TextPrimary,
    surface = Surface,
    onSurface = TextPrimary,
    surfaceVariant = Card,
    onSurfaceVariant = TextSecondary,
    outline = TextHint,
    outlineVariant = Divider,
    error = Error,
    onError = ColorTokens.White
)

private fun StoryNestColors.toLightColorScheme() = lightColorScheme(
    primary = Primary,
    onPrimary = ColorTokens.White,
    primaryContainer = Card,
    onPrimaryContainer = TextPrimary,
    secondary = Secondary,
    onSecondary = ColorTokens.White,
    tertiary = Accent,
    onTertiary = ColorTokens.White,
    background = Background,
    onBackground = TextPrimary,
    surface = Surface,
    onSurface = TextPrimary,
    surfaceVariant = Card,
    onSurfaceVariant = TextSecondary,
    outline = TextHint,
    outlineVariant = Divider,
    error = Error,
    onError = ColorTokens.White
)

private object ColorTokens {
    val White = androidx.compose.ui.graphics.Color(0xFFFFFFFF)
}

@Composable
fun StoryNestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) StoryNestDarkPalette else StoryNestLightPalette

    CompositionLocalProvider(LocalStoryNestColors provides colors) {
        MaterialTheme(
            colorScheme = if (darkTheme) {
                colors.toDarkColorScheme()
            } else {
                colors.toLightColorScheme()
            },
            typography = StoryNestTypography,
            shapes = StoryNestShapes,
            content = content
        )
    }
}
