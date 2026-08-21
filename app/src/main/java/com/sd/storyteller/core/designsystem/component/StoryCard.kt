package com.sd.storyteller.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sd.storyteller.domain.model.Story

/**
 * Created by SDHOLPURIA on 31-07-2026.
 */

@Composable
fun StoryCard(
    story: Story,
    onClick: () -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth()
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick
            )
    ) {

        Column(
            modifier =
                Modifier.padding(16.dp)
        ) {

            Text(
                text = story.title,
                style =
                    MaterialTheme.typography
                        .titleMedium
            )

            Text(
                text = story.content,
                maxLines = 2,
                style =
                    MaterialTheme.typography
                        .bodyMedium,
                modifier =
                    Modifier.padding(
                        top = 6.dp
                    )
            )
        }
    }
}