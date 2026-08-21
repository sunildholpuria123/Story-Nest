package com.sd.storyteller.feature.create.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sd.storyteller.core.constants.StoryTopics
import com.sd.storyteller.feature.create.model.StoryTopic

@Composable
fun StoryTopicSelector(
    selectedTopic: StoryTopic?,
    onTopicSelected: (StoryTopic?) -> Unit,
    modifier: Modifier = Modifier
) {

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        item {

            FilterChip(
                selected = selectedTopic == null,
                onClick = {
                    onTopicSelected(null)
                },
                label = {
                    Text("Any Topic")
                }
            )
        }

        items(
            items = StoryTopics.all,
            key = { it.id }
        ) { topic ->

            FilterChip(
                selected =
                    selectedTopic?.id == topic.id,

                onClick = {
                    onTopicSelected(topic)
                },

                label = {
                    Row(
                        horizontalArrangement =
                            Arrangement.spacedBy(4.dp)
                    ) {

                        Text(topic.emoji)

                        Text(topic.name)
                    }
                }
            )
        }
    }
}