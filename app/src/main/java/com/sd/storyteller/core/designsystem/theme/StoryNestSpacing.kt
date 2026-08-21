package com.sd.storyteller.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Created by SDHOLPURIA on 31-07-2026.
 */
@Immutable
data class StoryNestSpacing(

    val xs: Dp,

    val sm: Dp,

    val md: Dp,

    val lg: Dp,

    val xl: Dp,

    val xxl: Dp
)

val DefaultSpacing = StoryNestSpacing(

    xs = 4.dp,

    sm = 8.dp,

    md = 16.dp,

    lg = 24.dp,

    xl = 32.dp,

    xxl = 48.dp
)