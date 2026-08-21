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
import kotlin.math.PI
import kotlin.math.sin
import kotlin.random.Random

private data class Butterfly(

    val startX: Float,

    val startY: Float,

    val speed: Float,

    val size: Float,

    val phase: Float,

    val color: Color
)

@Composable
fun Butterflies(

    modifier: Modifier = Modifier,

    butterflyCount: Int = 10

) {

    val palette = listOf(

        Color(0xFFFF9800),

        Color(0xFFE91E63),

        Color(0xFF42A5F5),

        Color(0xFFFFEB3B),

        Color(0xFFAB47BC)
    )

    val butterflies = remember {

        List(butterflyCount) {

            Butterfly(

                startX = Random.nextFloat(),

                startY = Random.nextFloat(),

                speed = Random.nextFloat() * .45f + .55f,

                size = Random.nextFloat() * 10f + 12f,

                phase = Random.nextFloat() * (2f * PI.toFloat()),

                color = palette.random()
            )
        }
    }

    val transition = rememberInfiniteTransition(
        label = "butterflies"
    )

    val progress by transition.animateFloat(

        initialValue = 0f,

        targetValue = 1f,

        animationSpec = infiniteRepeatable(

            animation = tween(
                16000,
                easing = LinearEasing
            ),

            repeatMode = RepeatMode.Restart
        ),

        label = "progress"
    )

    Canvas(
        modifier = modifier.fillMaxSize()
    ) {

        butterflies.forEach { butterfly ->

            val x =
                ((butterfly.startX + progress * butterfly.speed) % 1f) *
                        size.width

            val y =
                butterfly.startY * size.height +
                        sin(progress * 20f + butterfly.phase) * 40f

            val flap =
                ((sin(progress * 45f + butterfly.phase) + 1f) / 2f)

            val wing =
                butterfly.size * (.5f + flap * .5f)

            drawCircle(

                color = butterfly.color,

                radius = wing,

                center = Offset(
                    x - wing * .7f,
                    y
                )
            )

            drawCircle(

                color = butterfly.color,

                radius = wing,

                center = Offset(
                    x + wing * .7f,
                    y
                )
            )

            drawLine(

                color = Color.DarkGray,

                start = Offset(x, y - wing),

                end = Offset(x, y + wing),

                strokeWidth = 2f
            )
        }
    }
}