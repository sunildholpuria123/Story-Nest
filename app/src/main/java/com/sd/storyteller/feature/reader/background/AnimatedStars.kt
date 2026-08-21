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

private data class Star(
    val x: Float,
    val y: Float,
    val radius: Float,
    val phase: Float,
    val baseAlpha: Float
)

@Composable
fun AnimatedStars(
    modifier: Modifier = Modifier,
    starCount: Int = 80,
    color: Color = Color.White
) {

    val stars = remember {

        List(starCount) {

            Star(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                radius = Random.nextFloat() * 3f + 1f,
                phase = Random.nextFloat() * (2f * PI.toFloat()),
                baseAlpha = Random.nextFloat() * 0.5f + 0.3f
            )
        }
    }

    val transition = rememberInfiniteTransition(
        label = "stars"
    )

    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 6000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "progress"
    )

    Canvas(
        modifier = modifier.fillMaxSize()
    ) {

        stars.forEach { star ->

            val alpha = star.baseAlpha +
                    0.35f * ((sin(progress + star.phase) + 1f) / 2f)

            drawCircle(
                color = color.copy(alpha = alpha.coerceIn(0f, 1f)),
                radius = star.radius,
                center = Offset(
                    x = size.width * star.x,
                    y = size.height * star.y
                )
            )
        }
    }
}