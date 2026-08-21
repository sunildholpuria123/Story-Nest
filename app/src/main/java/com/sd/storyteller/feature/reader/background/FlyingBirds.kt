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
import androidx.compose.ui.graphics.StrokeCap
import com.sd.storyteller.feature.reader.common.ThemeColors
import kotlin.math.PI
import kotlin.math.sin
import kotlin.random.Random

private data class Bird(
    val startX: Float,
    val y: Float,
    val speed: Float,
    val size: Float,
    val phase: Float
)

@Composable
fun FlyingBirds(
    modifier: Modifier = Modifier,
    birdCount: Int = 8,
    color: Color = ThemeColors.Adventure.Bird
) {

    val birds = remember {

        List(birdCount) {

            Bird(
                startX = Random.nextFloat(),
                y = Random.nextFloat() * 0.35f + 0.05f,
                speed = Random.nextFloat() * 0.5f + 0.6f,
                size = Random.nextFloat() * 8f + 10f,
                phase = Random.nextFloat() * (2f * PI.toFloat())
            )
        }
    }

    val transition = rememberInfiniteTransition(
        label = "birds"
    )

    val progress by transition.animateFloat(
        initialValue = -0.2f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 22000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "birdProgress"
    )

    Canvas(
        modifier = modifier.fillMaxSize()
    ) {

        birds.forEach { bird ->

            val x =
                ((bird.startX + progress * bird.speed) % 1.4f) * size.width

            val y =
                bird.y * size.height

            val flap =
                sin(progress * 30f + bird.phase) * (bird.size * 0.45f)

            drawLine(
                color = color,
                start = Offset(
                    x - bird.size,
                    y
                ),
                end = Offset(
                    x,
                    y - flap
                ),
                strokeWidth = 3f,
                cap = StrokeCap.Round
            )

            drawLine(
                color = color,
                start = Offset(
                    x,
                    y - flap
                ),
                end = Offset(
                    x + bird.size,
                    y
                ),
                strokeWidth = 3f,
                cap = StrokeCap.Round
            )
        }
    }
}