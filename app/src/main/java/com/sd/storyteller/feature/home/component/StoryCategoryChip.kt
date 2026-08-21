package com.sd.storyteller.feature.home.component

/**
 * Created by SDHOLPURIA on 31-07-2026.
 */


import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun StoryCategoryChip(
    name: String,
    emoji: String,
    onClick: () -> Unit
) {
    FilterChip(
        selected = false,
        onClick = onClick,
        label = {
            Text(
                text = "$emoji $name"
            )
        }
    )
}