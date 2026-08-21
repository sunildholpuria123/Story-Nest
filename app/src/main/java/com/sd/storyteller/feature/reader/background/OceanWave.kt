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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import com.sd.storyteller.feature.reader.common.ThemeColors
import kotlin.math.PI
import kotlin.math.sin

private data class WaveLayer(
    val amplitude: Float,
    val frequency: Float,
    val speed: Float,
    val verticalPosition: Float,
    val alpha: Float
)

@Composable
fun OceanWave(
    modifier: Modifier = Modifier,
    color: Color = ThemeColors.Ocean.Wave
) {

    val waves = remember {
        listOf(
            WaveLayer(
                amplitude = 18f,
                frequency = 1.8f,
                speed = 1.0f,
                verticalPosition = 0.78f,
                alpha = 0.22f
            ),
            WaveLayer(
                amplitude = 25f,
                frequency = 2.2f,
                speed = 0.75f,
                verticalPosition = 0.84f,
                alpha = 0.28f
            ),
            WaveLayer(
                amplitude = 15f,
                frequency = 2.8f,
                speed = 1.3f,
                verticalPosition = 0.90f,
                alpha = 0.35f
            )
        )
    }

    val transition = rememberInfiniteTransition(
        label = "oceanWaves"
    )

    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = (2f * PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 7000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "waveProgress"
    )

    Canvas(
        modifier = modifier.fillMaxSize()
    ) {

        waves.forEach { wave ->

            val path = Path()

            val baseY =
                size.height * wave.verticalPosition

            path.moveTo(
                0f,
                size.height
            )

            path.lineTo(
                0f,
                baseY
            )

            val step = 12f

            var x = 0f

            while (x <= size.width) {

                val normalizedX =
                    x / size.width

                val waveOffset =
                    sin(
                        normalizedX *
                                wave.frequency *
                                2f *
                                PI +
                                progress *
                                wave.speed
                    ) * wave.amplitude

                val y =
                    baseY + waveOffset

                path.lineTo(
                    x, y.toFloat()
                )

                x += step
            }

            path.lineTo(
                size.width,
                size.height
            )

            path.close()

            drawPath(
                path = path,
                color = color.copy(
                    alpha = wave.alpha
                )
            )
        }
    }
}