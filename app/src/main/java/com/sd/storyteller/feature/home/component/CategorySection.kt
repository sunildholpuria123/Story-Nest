package com.sd.storyteller.feature.home.component

/**
 * Created by SDHOLPURIA on 07-08-2026.
 */

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.FlowRow
import com.sd.storyteller.core.constants.StoryCategories
import com.sd.storyteller.core.designsystem.theme.StoryNestPalette

@Composable
fun CategorySection(
    onCategoryClick: (String) -> Unit,
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
            text = "Categories",
            color = StoryNestPalette.TextPrimary,
                    style = MaterialTheme.typography.titleLarge
        )

        // -------------------------------------------------
        // Category Chips
        // -------------------------------------------------

        FlowRow(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.spacedBy(8.dp),

            verticalArrangement =
                Arrangement.spacedBy(8.dp),

            maxItemsInEachRow = 3
        ) {

            StoryCategories.all.forEach { category ->

                StoryCategoryChip(

                    name = category.name,

                    emoji = category.emoji,

                    onClick = {
                        onCategoryClick(
                            category.id
                        )
                    }
                )
            }
        }
    }
}