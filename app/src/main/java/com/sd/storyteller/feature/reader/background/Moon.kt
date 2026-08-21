package com.sd.storyteller.feature.reader.background

/**
 * Created by SDHOLPURIA on 06-08-2026.
 */

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Moon(
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.TopEnd,

    moonSize: Dp = 110.dp
) {

    val transition = rememberInfiniteTransition(
        label = "moon"
    )

    val glow by transition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 3000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopEnd
    ) {

        Canvas(
            modifier = Modifier
                .size(110.dp)
        ) {

            val center = Offset(
                size.width / 2,
                size.height / 2
            )

            drawCircle(

                brush = Brush.radialGradient(

                    colors = listOf(

                        Color(0x55FFF8C4),

                        Color.Transparent
                    ),

                    center = center,

                    radius = size.minDimension * glow
                ),

                radius = size.minDimension * glow
            )

            drawCircle(

                color = Color(0xFFFFF3B0),

                radius = size.minDimension * .28f,

                center = center
            )

            drawCircle(

                color = Color(0x22FFFFFF),

                radius = size.minDimension * .08f,

                center = Offset(

                    center.x - 10f,

                    center.y - 8f
                )
            )

            drawCircle(

                color = Color(0x18FFFFFF),

                radius = size.minDimension * .05f,

                center = Offset(

                    center.x + 14f,

                    center.y + 12f
                )
            )
        }
    }
}