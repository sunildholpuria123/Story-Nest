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
import kotlin.random.Random

private data class Cloud(
    val y: Float,
    val radius: Float,
    val speed: Float,
    val alpha: Float,
    val startX: Float
)

@Composable
fun FloatingClouds(
    modifier: Modifier = Modifier,
    cloudCount: Int = 5,
    color: Color = Color.White.copy(alpha = 0.18f)
) {

    val clouds = remember {

        List(cloudCount) {

            Cloud(
                y = Random.nextFloat() * 0.45f,
                radius = Random.nextFloat() * 45f + 55f,
                speed = Random.nextFloat() * .4f + .6f,
                alpha = Random.nextFloat() * .12f + .10f,
                startX = Random.nextFloat()
            )
        }
    }

    val transition = rememberInfiniteTransition(
        label = "clouds"
    )

    val progress by transition.animateFloat(
        initialValue = -1f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 30000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "progress"
    )

    Canvas(
        modifier = modifier.fillMaxSize()
    ) {

        clouds.forEach { cloud ->

            val x =
                ((progress * cloud.speed) + cloud.startX)
                    .mod(2f)

            val centerX =
                (x - .5f) * size.width

            val centerY =
                cloud.y * size.height

            drawCircle(
                color = color.copy(alpha = cloud.alpha),
                radius = cloud.radius,
                center = Offset(
                    centerX,
                    centerY
                )
            )

            drawCircle(
                color = color.copy(alpha = cloud.alpha),
                radius = cloud.radius * .8f,
                center = Offset(
                    centerX - cloud.radius * .7f,
                    centerY + 8
                )
            )

            drawCircle(
                color = color.copy(alpha = cloud.alpha),
                radius = cloud.radius * .75f,
                center = Offset(
                    centerX + cloud.radius * .75f,
                    centerY + 10
                )
            )
        }
    }
}