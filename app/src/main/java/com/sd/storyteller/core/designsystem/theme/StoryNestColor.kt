package com.sd.storyteller.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color


/**
 * Created by SDHOLPURIA on 31-07-2026.
 */
@Immutable
data class StoryNestColors(

    // Brand
    val Primary: Color,
    val PrimaryDark: Color,
    val Secondary: Color,
    val Accent: Color,

    // Backgrounds
    val Background: Color,
    val Surface: Color,
    val Card: Color,

    // Text
    val TextPrimary: Color,
    val TextSecondary: Color,
    val TextHint: Color,

    // Status
    val Success: Color,
    val Error: Color,
    val Warning: Color,

    // Misc
    val Divider: Color,
    val Transparent: Color,

    )