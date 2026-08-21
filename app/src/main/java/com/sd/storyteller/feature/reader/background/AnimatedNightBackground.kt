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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedNightBackground() {

    val transition = rememberInfiniteTransition(
        label = "night"
    )

    val moonAlpha by transition.animateFloat(
        initialValue = .75f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(
                2500,
                easing = LinearEasing
            ),
            RepeatMode.Reverse
        ),
        label = "moon"
    )

    val cloudOffset by transition.animateFloat(
        initialValue = -40f,
        targetValue = 40f,
        animationSpec = infiniteRepeatable(
            tween(
                12000,
                easing = LinearEasing
            ),
            RepeatMode.Reverse
        ),
        label = "cloud"
    )

    val starAlpha by transition.animateFloat(
        initialValue = .3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(
                1500
            ),
            RepeatMode.Reverse
        ),
        label = "stars"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF07152F),
                        Color(0xFF182848),
                        Color(0xFF243B55)
                    )
                )
            )
    ) {

        Moon(
            moonAlpha
        )

        Clouds(
            cloudOffset
        )

        Stars(
            starAlpha
        )
    }

}

@Composable
private fun Moon(
    alpha: Float
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopEnd
    ) {

        Icon(
            imageVector = Icons.Default.DarkMode,
            contentDescription = null,
            tint = Color(0xFFFFF59D),
            modifier = Modifier
                .padding(28.dp)
                .size(80.dp)
                .alpha(alpha)
        )
    }
}

@Composable
private fun Clouds(
    offset: Float
) {

    Box(
        Modifier.fillMaxSize()
    ) {

        Icon(
            Icons.Default.Cloud,
            null,
            tint = Color.White.copy(.18f),
            modifier = Modifier
                .offset(
                    x = offset.dp,
                    y = 120.dp
                )
                .size(120.dp)
        )

        Icon(
            Icons.Default.Cloud,
            null,
            tint = Color.White.copy(.10f),
            modifier = Modifier
                .offset(
                    x = (-offset).dp,
                    y = 260.dp
                )
                .size(90.dp)
        )
    }
}

@Composable
private fun Stars(
    alpha: Float
) {

    Box(
        Modifier.fillMaxSize()
    ) {

        val stars = listOf(
            20.dp to 40.dp,
            120.dp to 90.dp,
            300.dp to 80.dp,
            60.dp to 220.dp,
            280.dp to 310.dp,
            170.dp to 380.dp,
            330.dp to 500.dp
        )

        stars.forEach {

            Icon(
                Icons.Default.Star,
                null,
                tint = Color.White.copy(alpha),
                modifier = Modifier
                    .offset(
                        x = it.first,
                        y = it.second
                    )
                    .size(12.dp)
            )
        }
    }
}