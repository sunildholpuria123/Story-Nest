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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import com.sd.storyteller.feature.reader.common.ThemeColors
import kotlin.math.PI
import kotlin.math.sin
import kotlin.random.Random

private data class Bubble(
    val startX: Float,
    val startY: Float,
    val radius: Float,
    val speed: Float,
    val phase: Float,
    val alpha: Float
)

@Composable
fun FloatingBubbles(
    modifier: Modifier = Modifier,
    bubbleCount: Int = 40,
    color: Color = ThemeColors.Ocean.Bubble
) {

    val bubbles = remember {

        List(bubbleCount) {

            Bubble(
                startX = Random.nextFloat(),
                startY = Random.nextFloat(),
                radius = Random.nextFloat() * 14f + 6f,
                speed = Random.nextFloat() * .45f + .55f,
                phase = Random.nextFloat() * (2f * PI.toFloat()),
                alpha = Random.nextFloat() * .35f + .25f
            )
        }
    }

    val transition = rememberInfiniteTransition(
        label = "bubbles"
    )

    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 12000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "bubbleProgress"
    )

    Canvas(
        modifier = modifier.fillMaxSize()
    ) {

        bubbles.forEach { bubble ->

            val y =
                size.height -
                        (((bubble.startY + progress * bubble.speed) % 1f)
                                * size.height)

            val drift =
                sin(progress * 10f + bubble.phase) * 20f

            val x =
                bubble.startX * size.width + drift

            drawCircle(
                color = color.copy(alpha = bubble.alpha),
                radius = bubble.radius,
                center = Offset(x, y),
                style = Stroke(
                    width = 2f
                )
            )

            drawCircle(
                color = Color.White.copy(alpha = bubble.alpha * .35f),
                radius = bubble.radius * .18f,
                center = Offset(
                    x - bubble.radius * .25f,
                    y - bubble.radius * .25f
                )
            )
        }
    }
}