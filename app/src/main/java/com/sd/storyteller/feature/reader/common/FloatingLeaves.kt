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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import kotlin.math.PI
import kotlin.math.sin
import kotlin.random.Random

private data class Leaf(
    val startX: Float,
    val startY: Float,
    val size: Float,
    val speed: Float,
    val phase: Float,
    val rotationOffset: Float
)

@Composable
fun FloatingLeaves(
    modifier: Modifier = Modifier,
    leafCount: Int = 24,
    color: Color = ThemeColors.Jungle.Leaf
) {

    val leaves = remember {

        List(leafCount) {

            Leaf(
                startX = Random.nextFloat(),
                startY = Random.nextFloat(),
                size = Random.nextFloat() * 18f + 12f,
                speed = Random.nextFloat() * .35f + .65f,
                phase = Random.nextFloat() * (2f * PI.toFloat()),
                rotationOffset = Random.nextFloat() * 360f
            )
        }
    }

    val transition = rememberInfiniteTransition(
        label = "leaves"
    )

    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 18000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "leafProgress"
    )

    Canvas(
        modifier = modifier.fillMaxSize()
    ) {

        leaves.forEach { leaf ->

            val y =
                ((leaf.startY + progress * leaf.speed) % 1f) * size.height

            val sway =
                sin(progress * 8f + leaf.phase) * 24f

            val x =
                leaf.startX * size.width + sway

            val rotation =
                progress * 360f + leaf.rotationOffset

            rotate(
                degrees = rotation,
                pivot = Offset(x, y)
            ) {

                val path = Path().apply {

                    moveTo(x, y - leaf.size)

                    quadraticBezierTo(
                        x + leaf.size,
                        y,
                        x,
                        y + leaf.size
                    )

                    quadraticBezierTo(
                        x - leaf.size,
                        y,
                        x,
                        y - leaf.size
                    )

                    close()
                }

                drawPath(
                    path = path,
                    color = color.copy(alpha = .80f)
                )

                drawLine(
                    color = Color.White.copy(alpha = .25f),
                    start = Offset(x, y - leaf.size * .8f),
                    end = Offset(x, y + leaf.size * .8f),
                    strokeWidth = 1.5f
                )
            }
        }
    }
}