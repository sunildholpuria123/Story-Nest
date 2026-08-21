package com.sd.storyteller.feature.home.component

/**
 * Created by SDHOLPURIA on 31-07-2026.
 */

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sd.storyteller.core.designsystem.theme.StoryNestPalette

@Composable
fun HomeSearchBar(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Surface(
        modifier = modifier.fillMaxWidth(),

        onClick = onClick,

        color = StoryNestPalette.Surface,

        tonalElevation = 2.dp
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 16.dp
                ),

            verticalAlignment =
                Alignment.CenterVertically,

            horizontalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {

            Icon(
                imageVector =
                    Icons.Outlined.Search,

                contentDescription = "Search",

                tint =
                    StoryNestPalette.TextSecondary
            )

            Text(
                text = "Search stories...",

                color =
                    StoryNestPalette.TextSecondary
            )
        }
    }
}