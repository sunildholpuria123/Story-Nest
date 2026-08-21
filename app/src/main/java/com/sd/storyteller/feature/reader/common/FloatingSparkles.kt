package com.sd.storyteller.feature.reader.common

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
import kotlin.math.PI
import kotlin.math.cos
import kotlin.random.Random

private data class Sparkle(
    val x: Float,
    val y: Float,
    val size: Float,
    val phase: Float,
    val alpha: Float
)

@Composable
fun FloatingSparkles(
    modifier: Modifier = Modifier,
    sparkleCount: Int = 45,
    color: Color = ThemeColors.Magic.Sparkle
) {

    val sparkles = remember {

        List(sparkleCount) {

            Sparkle(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                size = Random.nextFloat() * 6f + 4f,
                phase = Random.nextFloat() * (2f * PI.toFloat()),
                alpha = Random.nextFloat() * .5f + .3f
            )
        }
    }

    val transition = rememberInfiniteTransition(
        label = "sparkles"
    )

    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 5000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "sparkleProgress"
    )

    Canvas(
        modifier = modifier.fillMaxSize()
    ) {

        sparkles.forEach { sparkle ->

            val pulse =
                ((cos(progress + sparkle.phase) + 1f) / 2f)

            val radius =
                sparkle.size * (0.7f + pulse * 0.6f)

            val alpha =
                (sparkle.alpha * (0.4f + pulse * 0.6f))
                    .coerceIn(0f, 1f)

            val cx = sparkle.x * size.width
            val cy = sparkle.y * size.height

            drawLine(
                color = color.copy(alpha = alpha),
                start = Offset(cx - radius, cy),
                end = Offset(cx + radius, cy),
                strokeWidth = 2f
            )

            drawLine(
                color = color.copy(alpha = alpha),
                start = Offset(cx, cy - radius),
                end = Offset(cx, cy + radius),
                strokeWidth = 2f
            )

            drawCircle(
                color = color.copy(alpha = alpha),
                radius = radius * .20f,
                center = Offset(cx, cy)
            )
        }
    }
}