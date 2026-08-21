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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.math.sin

private data class Tree(
    val x: Float,
    val height: Float,
    val width: Float,
    val layer: Int,
    val phase: Float
)

@Composable
fun ForestTrees(
    modifier: Modifier = Modifier,
    treeCount: Int = 14,
    backColor: Color = Color(0xFF2E7D32),
    middleColor: Color = Color(0xFF1B5E20),
    frontColor: Color = Color(0xFF104D1A)
) {

    val trees = remember(treeCount) {

        List(treeCount) { index ->

            Tree(
                x = index.toFloat() / treeCount,
                height = 0.25f + (index % 4) * 0.045f,
                width = 0.10f + (index % 3) * 0.015f,
                layer = index % 3,
                phase = index * 0.7f
            )
        }
    }

    val transition = rememberInfiniteTransition(
        label = "forest"
    )

    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 12000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "forestProgress"
    )

    Canvas(
        modifier = modifier.fillMaxSize()
    ) {

        trees.forEach { tree ->

            val layerColor = when (tree.layer) {
                0 -> backColor
                1 -> middleColor
                else -> frontColor
            }

            val baseY = size.height

            val treeHeight =
                size.height * tree.height

            val treeWidth =
                size.width * tree.width

            val sway =
                sin(
                    progress * 6f + tree.phase
                ) * 3f

            val centerX =
                tree.x * size.width + sway

            // Trunk
            drawRect(
                color = Color(0xFF5D4037),
                topLeft = Offset(
                    centerX - treeWidth * .08f,
                    baseY - treeHeight * .45f
                ),
                size = androidx.compose.ui.geometry.Size(
                    treeWidth * .16f,
                    treeHeight * .45f
                )
            )

            // Bottom foliage
            drawCircle(
                color = layerColor,
                radius = treeWidth * .42f,
                center = Offset(
                    centerX,
                    baseY - treeHeight * .35f
                )
            )

            // Middle foliage
            drawCircle(
                color = layerColor,
                radius = treeWidth * .34f,
                center = Offset(
                    centerX - treeWidth * .18f,
                    baseY - treeHeight * .55f
                )
            )

            drawCircle(
                color = layerColor,
                radius = treeWidth * .36f,
                center = Offset(
                    centerX + treeWidth * .18f,
                    baseY - treeHeight * .55f
                )
            )

            // Top foliage
            drawCircle(
                color = layerColor,
                radius = treeWidth * .30f,
                center = Offset(
                    centerX,
                    baseY - treeHeight * .76f
                )
            )
        }
    }
}