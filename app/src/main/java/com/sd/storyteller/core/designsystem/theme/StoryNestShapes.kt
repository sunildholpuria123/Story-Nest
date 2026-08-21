package com.sd.storyteller.core.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import com.sd.storyteller.core.designsystem.dimention.StoryNestDimens

/**
 * Created by SDHOLPURIA on 31-07-2026.
 */

/**
 * Centralized application shapes.
 *
 * All Material3 components will use these shapes.
 */
val StoryNestShapes = Shapes(

    extraSmall = RoundedCornerShape(
        StoryNestDimens.RadiusSmall
    ),

    small = RoundedCornerShape(
        StoryNestDimens.RadiusMedium
    ),

    medium = RoundedCornerShape(
        StoryNestDimens.RadiusLarge
    ),

    large = RoundedCornerShape(
        StoryNestDimens.RadiusXLarge
    ),

    extraLarge = RoundedCornerShape(
        StoryNestDimens.RadiusCircle
    )
)