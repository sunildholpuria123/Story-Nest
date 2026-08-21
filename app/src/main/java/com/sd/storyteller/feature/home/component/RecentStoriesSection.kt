package com.sd.storyteller.feature.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sd.storyteller.core.designsystem.component.StoryCard
import com.sd.storyteller.core.designsystem.theme.StoryNestPalette
import com.sd.storyteller.domain.model.Story

@Composable
fun RecentStoriesSection(
    stories: List<Story>,
    onStoryClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // -------------------------------------------------
        // Section Title
        // -------------------------------------------------

        Text(
            text = "Recent Stories",
            color = StoryNestPalette.TextPrimary,
            style = MaterialTheme.typography.titleLarge
        )

        // -------------------------------------------------
        // Empty State
        // -------------------------------------------------

        if (stories.isEmpty()) {

            Text(
                text = "No recently read stories yet.",
                style = MaterialTheme.typography.bodyMedium
            )

        } else {

            // -------------------------------------------------
            // Stories - Vertical
            // -------------------------------------------------

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                stories.forEach { story ->

                    StoryCard(
                        story = story,
                        onClick = {
                            onStoryClick(story.id)
                        }
                    )
                }
            }
        }
    }
}