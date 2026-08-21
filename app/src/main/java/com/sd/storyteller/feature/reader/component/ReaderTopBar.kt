package com.sd.storyteller.feature.reader.component

/**
 * Created by SDHOLPURIA on 05-08-2026.
 */

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderTopBar(
    title: String,
    onBack: () -> Unit,
    onShare: () -> Unit
) {

    TopAppBar(

        title = {
            Text(title)
        },

        navigationIcon = {

            IconButton(
                onClick = onBack
            ) {

                Icon(
                    Icons.Outlined.ArrowBack,
                    null
                )
            }
        },

        actions = {

            IconButton(
                onClick = onShare
            ) {

                Icon(
                    Icons.Outlined.Share,
                    null
                )
            }
        }
    )
}