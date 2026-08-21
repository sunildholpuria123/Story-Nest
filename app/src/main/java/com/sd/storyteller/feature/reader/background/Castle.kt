package com.sd.storyteller.feature.reader.background

/**
 * Created by SDHOLPURIA on 07-08-2026.
 */

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.min

@Composable
fun Castle(
    modifier: Modifier = Modifier,
    wallColor: Color = Color(0xFFFFF1F7),
    roofColor: Color = Color(0xFFAD4D9B),
    doorColor: Color = Color(0xFF7B3F72),
    windowColor: Color = Color(0xFFFFE082),
    flagColor: Color = Color(0xFFE91E63)
) {

    Canvas(
        modifier = modifier.fillMaxSize()
    ) {

        val scale = min(
            size.width,
            size.height
        ) / 420f

        val centerX = size.width / 2f

        val groundY = size.height * .88f

        val mainWidth = 180f * scale
        val mainHeight = 125f * scale

        val towerWidth = 58f * scale
        val towerHeight = 175f * scale

        val mainLeft =
            centerX - mainWidth / 2f

        val mainTop =
            groundY - mainHeight

        // Main castle
        drawRect(
            color = wallColor,
            topLeft = Offset(
                mainLeft,
                mainTop
            ),
            size = Size(
                mainWidth,
                mainHeight
            )
        )

        // Left tower
        drawRect(
            color = wallColor,
            topLeft = Offset(
                mainLeft - towerWidth + 12f * scale,
                groundY - towerHeight
            ),
            size = Size(
                towerWidth,
                towerHeight
            )
        )

        // Right tower
        drawRect(
            color = wallColor,
            topLeft = Offset(
                mainLeft + mainWidth - 12f * scale,
                groundY - towerHeight
            ),
            size = Size(
                towerWidth,
                towerHeight
            )
        )

        // Main roof
        drawRoof(
            centerX = centerX,
            baseY = mainTop,
            width = mainWidth,
            height = 55f * scale,
            color = roofColor
        )

        // Left roof
        drawRoof(
            centerX = mainLeft - towerWidth / 2f + 12f * scale,
            baseY = groundY - towerHeight,
            width = towerWidth,
            height = 55f * scale,
            color = roofColor
        )

        // Right roof
        drawRoof(
            centerX = mainLeft + mainWidth + towerWidth / 2f - 12f * scale,
            baseY = groundY - towerHeight,
            width = towerWidth,
            height = 55f * scale,
            color = roofColor
        )

        // Main door
        drawRoundRect(
            color = doorColor,
            topLeft = Offset(
                centerX - 20f * scale,
                groundY - 65f * scale
            ),
            size = Size(
                40f * scale,
                65f * scale
            ),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(
                20f * scale,
                20f * scale
            )
        )

        // Windows
        drawWindow(
            center = Offset(
                centerX,
                mainTop + 48f * scale
            ),
            radius = 13f * scale,
            color = windowColor
        )

        drawWindow(
            center = Offset(
                mainLeft + towerWidth / 2f - 10f * scale,
                groundY - towerHeight + 75f * scale
            ),
            radius = 11f * scale,
            color = windowColor
        )

        drawWindow(
            center = Offset(
                mainLeft + mainWidth + towerWidth / 2f - 10f * scale,
                groundY - towerHeight + 75f * scale
            ),
            radius = 11f * scale,
            color = windowColor
        )

        // Flags
        drawFlag(
            x = mainLeft - towerWidth / 2f + 12f * scale,
            topY = groundY - towerHeight - 55f * scale,
            scale = scale,
            color = flagColor
        )

        drawFlag(
            x = mainLeft + mainWidth + towerWidth / 2f - 12f * scale,
            topY = groundY - towerHeight - 55f * scale,
            scale = scale,
            color = flagColor
        )

        drawFlag(
            x = centerX,
            topY = mainTop - 60f * scale,
            scale = scale,
            color = flagColor
        )
    }
}

private fun DrawScope.drawRoof(
    centerX: Float,
    baseY: Float,
    width: Float,
    height: Float,
    color: Color
) {

    val path = Path()

    path.moveTo(
        centerX - width / 2f,
        baseY
    )

    path.lineTo(
        centerX,
        baseY - height
    )

    path.lineTo(
        centerX + width / 2f,
        baseY
    )

    path.close()

    drawPath(
        path = path,
        color = color
    )
}

private fun DrawScope.drawWindow(
    center: Offset,
    radius: Float,
    color: Color
) {

    drawCircle(
        color = color,
        radius = radius,
        center = center
    )

    drawLine(
        color = Color.White.copy(alpha = .6f),
        start = Offset(
            center.x - radius,
            center.y
        ),
        end = Offset(
            center.x + radius,
            center.y
        ),
        strokeWidth = 2f
    )

    drawLine(
        color = Color.White.copy(alpha = .6f),
        start = Offset(
            center.x,
            center.y - radius
        ),
        end = Offset(
            center.x,
            center.y + radius
        ),
        strokeWidth = 2f
    )
}

private fun DrawScope.drawFlag(
    x: Float,
    topY: Float,
    scale: Float,
    color: Color
) {

    drawLine(
        color = Color(0xFF795548),
        start = Offset(
            x,
            topY
        ),
        end = Offset(
            x,
            topY + 65f * scale
        ),
        strokeWidth = 3f * scale
    )

    val flag = Path()

    flag.moveTo(
        x,
        topY
    )

    flag.lineTo(
        x + 25f * scale,
        topY + 10f * scale
    )

    flag.lineTo(
        x,
        topY + 25f * scale
    )

    flag.close()

    drawPath(
        path = flag,
        color = color
    )
}