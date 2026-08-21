package com.sd.storyteller.feature.reader.common

/**
 * Created by SDHOLPURIA on 06-08-2026.
 */

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun AnimatedGradient(
    colors: List<Color>,
    modifier: Modifier = Modifier
) {

    require(colors.size >= 3) {
        "AnimatedGradient requires at least 3 colors."
    }

    val transition = rememberInfiniteTransition(
        label = "gradient"
    )

    val color1 by transition.animateColor(
        initialValue = colors[0],
        targetValue = colors[1],
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 12000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "color1"
    )

    val color2 by transition.animateColor(
        initialValue = colors[1],
        targetValue = colors[2],
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 15000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "color2"
    )

    val color3 by transition.animateColor(
        initialValue = colors[2],
        targetValue = colors[0],
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 18000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "color3"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        color1,
                        color2,
                        color3
                    )
                )
            )
    )
}