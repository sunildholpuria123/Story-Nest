package com.sd.storyteller.core.designsystem.component

/**
 * Created by SDHOLPURIA on 31-07-2026.
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlin.math.sin

/**
 * Enchanted fantasy background for Magic stories.
 */
@Composable
fun MagicBackground(
    modifier: Modifier = Modifier
) {

    val infiniteTransition =
        rememberInfiniteTransition(
            label = "magicBackground"
        )

    val animation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 6000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "magicAnimation"
    )

    Canvas(
        modifier = modifier.fillMaxSize()
    ) {

        // -------------------------------------------------
        // Background
        // -------------------------------------------------

        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF17102B),
                    Color(0xFF281642),
                    Color(0xFF120D24)
                )
            )
        )

        // -------------------------------------------------
        // Floating magical particles
        // -------------------------------------------------

        val particles = listOf(
            Offset(
                x = size.width * 0.15f,
                y = size.height * 0.18f
            ),
            Offset(
                x = size.width * 0.78f,
                y = size.height * 0.16f
            ),
            Offset(
                x = size.width * 0.45f,
                y = size.height * 0.28f
            ),
            Offset(
                x = size.width * 0.88f,
                y = size.height * 0.42f
            ),
            Offset(
                x = size.width * 0.22f,
                y = size.height * 0.55f
            ),
            Offset(
                x = size.width * 0.68f,
                y = size.height * 0.64f
            ),
            Offset(
                x = size.width * 0.38f,
                y = size.height * 0.78f
            ),
            Offset(
                x = size.width * 0.82f,
                y = size.height * 0.82f
            )
        )

        particles.forEachIndexed { index, point ->

            val wave =
                sin(
                    (animation * 6f) + index
                )

            val radius =
                3f + (wave + 1f) * 2f

            val alpha =
                0.25f + (wave + 1f) * 0.18f

            drawCircle(
                color = Color(0xFFE7C6FF).copy(
                    alpha = alpha
                ),
                radius = radius,
                center = Offset(
                    x = point.x,
                    y = point.y + wave * 12f
                )
            )
        }

        // -------------------------------------------------
        // Magical glow
        // -------------------------------------------------

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFFB86CFF).copy(alpha = 0.18f),
                    Color.Transparent
                )
            ),
            radius = size.width * 0.65f,
            center = Offset(
                x = size.width * 0.5f,
                y = size.height * 0.35f
            )
        )
    }
}