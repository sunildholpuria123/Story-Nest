package com.sd.storyteller.feature.reader.background

/**
 * Created by SDHOLPURIA on 07-08-2026.
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import kotlin.math.PI
import kotlin.math.sin

@Composable
fun Mountains(
    modifier: Modifier = Modifier,
    backColor: Color = Color(0xFF78909C),
    middleColor: Color = Color(0xFF607D8B),
    frontColor: Color = Color(0xFF455A64)
) {

    val transition = rememberInfiniteTransition(
        label = "mountains"
    )

    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = (2f * PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 30000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "mountainProgress"
    )

    Canvas(
        modifier = modifier.fillMaxSize()
    ) {

        drawMountainLayer(
            color = backColor,
            baseY = size.height * .72f,
            height = size.height * .28f,
            frequency = 2.0f,
            offset = progress * .15f
        )

        drawMountainLayer(
            color = middleColor,
            baseY = size.height * .80f,
            height = size.height * .25f,
            frequency = 2.5f,
            offset = progress * .30f
        )

        drawMountainLayer(
            color = frontColor,
            baseY = size.height * .90f,
            height = size.height * .22f,
            frequency = 3.0f,
            offset = progress * .45f
        )
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawMountainLayer(
    color: Color,
    baseY: Float,
    height: Float,
    frequency: Float,
    offset: Float
) {

    val path = Path()

    path.moveTo(
        0f,
        size.height
    )

    path.lineTo(
        0f,
        baseY
    )

    val points = 12

    for (index in 0..points) {

        val x =
            size.width * index / points

        val wave =
            sin(
                index / points.toFloat() *
                        frequency *
                        2f *
                        PI +
                        offset
            )

        val y =
            baseY -
                    ((wave + 1f) / 2f) *
                    height

        path.lineTo(
            x,
            y.toFloat()
        )
    }

    path.lineTo(
        size.width,
        size.height
    )

    path.close()

    drawPath(
        path = path,
        color = color
    )
}