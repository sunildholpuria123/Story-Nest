package com.sd.storyteller.feature.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.sd.storyteller.core.designsystem.dimention.StoryNestDimens
import com.sd.storyteller.core.designsystem.theme.StoryNestPalette

/**
 * Created by SDHOLPURIA on 31-07-2026.
 */
@Composable
fun WelcomeSection() {

    Column {

        Text(
            text = "Welcome 👋",
            style = MaterialTheme.typography.headlineMedium,
            color = StoryNestPalette.TextPrimary
        )

        Spacer(
            modifier = androidx.compose.ui.Modifier.height(
                StoryNestDimens.Space8
            )
        )

        Text(
            text = "Ready for a magical adventure today?",
            style = MaterialTheme.typography.bodyLarge,
            color = StoryNestPalette.TextSecondary
        )
    }
}