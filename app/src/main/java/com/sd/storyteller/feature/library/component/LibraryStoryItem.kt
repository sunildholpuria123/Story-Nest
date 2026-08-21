package com.sd.storyteller.feature.library.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sd.storyteller.core.designsystem.component.StoryCard
import com.sd.storyteller.domain.model.Story

/**
 * Created by SDHOLPURIA on 07-08-2026.
 */

@Composable
fun LibraryStoryItem(
    story: Story,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {

    Row(
        modifier =
            Modifier.fillMaxWidth(),
        horizontalArrangement =
            Arrangement.spacedBy(8.dp)
    ) {

        StoryCard(
            story = story,
            onClick = onClick,
        )

        Surface(
            shape =
                MaterialTheme.shapes.medium
        ) {

            IconButton(
                onClick = onDelete
            ) {

                Icon(
                    imageVector =
                        Icons.Outlined.Delete,
                    contentDescription =
                        "Delete ${story.title}"
                )
            }
        }
    }
}