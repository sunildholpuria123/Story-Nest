package com.sd.storyteller.feature.reader.background

/**
 * Created by SDHOLPURIA on 07-08-2026.
 */

import AnimatedStars
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sd.storyteller.core.designsystem.component.MagicBackground
import com.sd.storyteller.feature.reader.common.AnimatedGradient
import com.sd.storyteller.feature.reader.common.FloatingLeaves
import com.sd.storyteller.feature.reader.common.FloatingSparkles
import com.sd.storyteller.feature.reader.common.ThemeColors
import com.sd.storyteller.ui.theme.StoryTheme

@Composable
fun ThemeBackground(
    theme: StoryTheme,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        when (theme) {

            StoryTheme.BEDTIME -> {
                BedtimeBackground()
            }

            StoryTheme.JUNGLE -> {
                JungleBackground()
            }

            StoryTheme.OCEAN -> {
                OceanBackground()
            }

            StoryTheme.SPACE -> {
                SpaceBackground()
            }

            StoryTheme.MAGIC -> {
                MagicBackground()
            }

            StoryTheme.PRINCESS -> {
                PrincessBackground()
            }

            StoryTheme.ADVENTURE -> {
                AdventureBackground()
            }

            StoryTheme.ANIMALS -> {
                AnimalsBackground()
            }
        }
    }
}


@Composable
fun BedtimeBackground() {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        AnimatedGradient(
            colors = listOf(
                ThemeColors.Bedtime.Top,
                ThemeColors.Bedtime.Middle,
                ThemeColors.Bedtime.Bottom
            )
        )

        AnimatedStars(
            starCount = 70,
            color = ThemeColors.Bedtime.Star
        )

        FloatingClouds(
            cloudCount = 3,
            color = ThemeColors.Bedtime.Cloud
        )

        Moon(
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun JungleBackground() {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        AnimatedGradient(
            colors = listOf(
                ThemeColors.Jungle.Top,
                ThemeColors.Jungle.Middle,
                ThemeColors.Jungle.Bottom
            )
        )

        Sun(
            alignment = Alignment.TopEnd,
            color = ThemeColors.Jungle.Sun
        )

        FloatingClouds(
            cloudCount = 4,
            color = ThemeColors.Jungle.Cloud
        )

        ForestTrees(
            treeCount = 16
        )

        FloatingLeaves(
            leafCount = 20,
            color = ThemeColors.Jungle.Leaf
        )

        FlyingBirds(
            birdCount = 6,
            color = ThemeColors.Adventure.Bird
        )
    }
}

@Composable
fun OceanBackground() {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        AnimatedGradient(
            colors = listOf(
                ThemeColors.Ocean.Top,
                ThemeColors.Ocean.Middle,
                ThemeColors.Ocean.Bottom
            )
        )

        Sun(
            alignment = Alignment.TopCenter,
            color = ThemeColors.Ocean.Sun
        )

        FloatingBubbles(
            bubbleCount = 35,
            color = ThemeColors.Ocean.Bubble
        )

        OceanWave(
            color = ThemeColors.Ocean.Wave
        )
    }
}

@Composable
fun SpaceBackground() {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        AnimatedGradient(
            colors = listOf(
                ThemeColors.Space.Top,
                ThemeColors.Space.Middle,
                ThemeColors.Space.Bottom
            )
        )

        AnimatedStars(
            starCount = 110,
            color = ThemeColors.Space.Star
        )

        Planet(
            alignment = Alignment.CenterEnd,
            planetSize = 190.dp,
            planetColor = ThemeColors.Space.Planet,
            showRing = true
        )
    }
}

@Composable
private fun MagicBackground() {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        AnimatedGradient(
            colors = listOf(
                ThemeColors.Magic.Top,
                ThemeColors.Magic.Middle,
                ThemeColors.Magic.Bottom
            )
        )

        AnimatedStars(
            starCount = 50,
            color = ThemeColors.Magic.Sparkle
        )

        FloatingSparkles(
            sparkleCount = 55,
            color = ThemeColors.Magic.Sparkle
        )

        FloatingClouds(
            cloudCount = 3,
            color = ThemeColors.Magic.Glow
        )
    }
}

@Composable
fun PrincessBackground() {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        AnimatedGradient(
            colors = listOf(
                ThemeColors.Princess.Top,
                ThemeColors.Princess.Middle,
                ThemeColors.Princess.Bottom
            )
        )

        FloatingClouds(
            cloudCount = 4,
            color = Color.White.copy(alpha = .20f)
        )

        FloatingSparkles(
            sparkleCount = 50,
            color = ThemeColors.Princess.Sparkle
        )

        Castle()
    }
}

@Composable
fun AdventureBackground() {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        AnimatedGradient(
            colors = listOf(
                ThemeColors.Adventure.Top,
                ThemeColors.Adventure.Middle,
                ThemeColors.Adventure.Bottom
            )
        )

        Sun(
            alignment = Alignment.TopEnd,
            color = ThemeColors.Adventure.Sun
        )

        FloatingClouds(
            cloudCount = 4
        )

        FlyingBirds(
            birdCount = 7,
            color = ThemeColors.Adventure.Bird
        )

        Mountains()
    }
}

@Composable
fun AnimalsBackground() {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        AnimatedGradient(
            colors = listOf(
                ThemeColors.Animals.Top,
                ThemeColors.Animals.Middle,
                ThemeColors.Animals.Bottom
            )
        )

        Sun(
            alignment = Alignment.TopEnd
        )

        ForestTrees(
            treeCount = 12
        )

        FloatingLeaves(
            leafCount = 12,
            color = ThemeColors.Animals.Leaf
        )

        Butterflies(
            butterflyCount = 8
        )
    }
}