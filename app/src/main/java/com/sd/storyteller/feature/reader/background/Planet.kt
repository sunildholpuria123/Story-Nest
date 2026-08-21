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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sd.storyteller.feature.reader.common.ThemeColors

@Composable
fun Planet(
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.CenterEnd,
    planetSize: Dp = 180.dp,
    planetColor: Color = ThemeColors.Space.Planet,
    atmosphereColor: Color = Color(0xFFB39DDB),
    showRing: Boolean = true
) {

    val transition = rememberInfiniteTransition(
        label = "planet"
    )

    val glow by transition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 5000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "planetGlow"
    )

    val rotation by transition.animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 12000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "planetRotation"
    )

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = alignment
    ) {

        Canvas(
            modifier = Modifier.size(planetSize)
        ) {

            val center = Offset(
                size.width / 2f,
                size.height / 2f
            )

            val radius =
                size.minDimension * .30f

            // Atmosphere glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        atmosphereColor.copy(
                            alpha = .40f
                        ),
                        Color.Transparent
                    ),
                    center = center,
                    radius = radius * 2.2f * glow
                ),
                radius = radius * 2.2f * glow,
                center = center
            )

            if (showRing) {

                rotate(
                    degrees = rotation,
                    pivot = center
                ) {

                    drawOval(
                        color = atmosphereColor.copy(
                            alpha = .45f
                        ),
                        topLeft = Offset(
                            center.x - radius * 1.7f,
                            center.y - radius * .35f
                        ),
                        size = androidx.compose.ui.geometry.Size(
                            radius * 3.4f,
                            radius * .7f
                        ),
                        style = androidx.compose.ui.graphics.drawscope.Stroke(
                            width = radius * .12f
                        )
                    )
                }
            }

            // Planet surface
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        planetColor.copy(
                            alpha = 1f
                        ),
                        planetColor.copy(
                            alpha = .75f
                        ),
                        Color(0xFF211044)
                    ),
                    center = Offset(
                        center.x - radius * .25f,
                        center.y - radius * .25f
                    ),
                    radius = radius * 1.4f
                ),
                radius = radius,
                center = center
            )

            // Surface details
            drawCircle(
                color = Color.White.copy(alpha = .08f),
                radius = radius * .25f,
                center = Offset(
                    center.x - radius * .30f,
                    center.y - radius * .10f
                )
            )

            drawCircle(
                color = Color.Black.copy(alpha = .10f),
                radius = radius * .18f,
                center = Offset(
                    center.x + radius * .30f,
                    center.y + radius * .20f
                )
            )
        }
    }
}