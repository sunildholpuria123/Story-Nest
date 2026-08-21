package com.sd.storyteller.feature.reader.component

/**
 * Created by SDHOLPURIA on 07-08-2026.
 */

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ReadingProgress(
    currentSentence: Int,
    totalSentences: Int,
    modifier: Modifier = Modifier
) {

    if (totalSentences <= 0) {
        return
    }

    val current =
        (currentSentence + 1)
            .coerceIn(1, totalSentences)

    val progress =
        current.toFloat() / totalSentences.toFloat()

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.SpaceBetween
        ) {

            Text(
                text = "Reading",
                style =
                    MaterialTheme.typography.labelMedium
            )

            Text(
                text =
                    "$current / $totalSentences",
                style =
                    MaterialTheme.typography.labelMedium
            )
        }

        LinearProgressIndicator(
            progress = {
                progress
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
