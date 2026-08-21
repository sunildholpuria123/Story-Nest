package com.sd.storyteller.feature.settings.component

/**
 * Created by SDHOLPURIA on 08-08-2026.
 */

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sd.storyteller.core.designsystem.theme.StoryNestPalette

@Composable
fun SettingsSwitchRow(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = 12.dp
            ),
        horizontalArrangement =
            Arrangement.SpaceBetween
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp)
        ) {

            Text(
                text = title,
                style =
                    MaterialTheme.typography.titleMedium,
                color =
                    StoryNestPalette.TextPrimary
            )

            Text(
                text = description,
                style =
                    MaterialTheme.typography.bodyMedium,
                color =
                    StoryNestPalette.TextSecondary,
                modifier =
                    Modifier.padding(
                        top = 4.dp
                    )
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}