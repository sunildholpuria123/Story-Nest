package com.sd.storyteller.feature.home.component

/**
 * Created by SDHOLPURIA on 31-07-2026.
 */

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sd.storyteller.core.designsystem.dimention.StoryNestDimens
import com.sd.storyteller.core.designsystem.theme.StoryNestPalette

@Composable
fun CreateStoryCard(
    onCreateStory: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = StoryNestPalette.Card
        )
    ) {

        Column(
            modifier = Modifier.padding(
                StoryNestDimens.Space20
            ),
            verticalArrangement = Arrangement.spacedBy(
                StoryNestDimens.Space16
            )
        ) {

            Text(
                text = "✨ Create a New Adventure",
                style = MaterialTheme.typography.titleLarge,
                color = StoryNestPalette.TextPrimary
            )

            Text(
                text = "Generate magical stories with AI in just a few seconds.",
                style = MaterialTheme.typography.bodyLarge,
                color = StoryNestPalette.TextSecondary
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onCreateStory
            ) {
                Text("Start Creating")
            }
        }
    }
}