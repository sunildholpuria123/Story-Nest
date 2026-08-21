package com.sd.storyteller.feature.splash.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.sd.storyteller.core.designsystem.theme.StoryNestPalette
import com.sd.storyteller.core.navigation.Screen
import kotlinx.coroutines.delay

/**
 * Created by SDHOLPURIA on 31-07-2026.
 */
@Composable
fun SplashScreen(
    navController: NavController
) {

    LaunchedEffect(Unit) {
        delay(2000)

        navController.navigate(Screen.Home.route) {
            popUpTo(Screen.Splash.route) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StoryNestPalette.Background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "📖",
            style = MaterialTheme.typography.displayLarge
        )

        Text(
            text = "StoryNest",
            style = MaterialTheme.typography.headlineLarge,
            color = StoryNestPalette.TextPrimary
        )

        Text(
            text = "Every Story Comes Alive ✨",
            style = MaterialTheme.typography.bodyLarge,
            color = StoryNestPalette.TextSecondary
        )
    }
}