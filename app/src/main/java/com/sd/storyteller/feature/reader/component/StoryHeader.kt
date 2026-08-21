package com.sd.storyteller.feature.reader.component

/**
 * Created by SDHOLPURIA on 05-08-2026.
 */

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sd.storyteller.core.designsystem.dimention.StoryNestDimens

@Composable
fun StoryHeader(
    title: String
) {

    Column(

        modifier = Modifier.fillMaxWidth(),

        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Text(
            text = "📖",
            style = MaterialTheme.typography.displayMedium
        )

        Spacer(
            modifier = Modifier.height(
                StoryNestDimens.Space16
            )
        )

        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}