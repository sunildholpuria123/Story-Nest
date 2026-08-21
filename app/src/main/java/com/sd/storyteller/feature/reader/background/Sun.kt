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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sd.storyteller.feature.reader.common.ThemeColors

@Composable
fun Sun(
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.TopStart,
    sunSize: Dp = 120.dp,
    color: Color = ThemeColors.Jungle.Sun
) {

    val transition = rememberInfiniteTransition(
        label = "sun"
    )

    val glow by transition.animateFloat(
        initialValue = 0.90f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 3500,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 90000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = alignment
    ) {

        Canvas(
            modifier = Modifier.size(sunSize)
        ) {

            val center = Offset(
                size.width / 2,
                size.height / 2
            )

            // Glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        color.copy(alpha = .35f),
                        Color.Transparent
                    ),
                    center = center,
                    radius = size.minDimension * glow
                ),
                radius = size.minDimension * glow,
                center = center
            )

            rotate(rotation) {

                repeat(12) { index ->

                    rotate(index * 30f) {

                        drawRoundRect(
                            color = color.copy(alpha = .45f),
                            topLeft = Offset(
                                center.x - 3f,
                                center.y - size.minDimension * .40f
                            ),
                            size = Size(
                                6f,
                                size.minDimension * .16f
                            )
                        )
                    }
                }
            }

            // Core Sun
            drawCircle(
                color = color,
                radius = size.minDimension * .24f,
                center = center
            )
        }
    }
}