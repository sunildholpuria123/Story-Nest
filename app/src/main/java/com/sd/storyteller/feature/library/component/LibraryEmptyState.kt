package com.sd.storyteller.feature.library.component


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sd.storyteller.feature.library.ui.LibraryTab

/**
 * Created by SDHOLPURIA on 07-08-2026.
 */

@Composable
fun LibraryEmptyState(

    tab: LibraryTab,

    modifier: Modifier = Modifier

) {

    val message = when (tab) {

        LibraryTab.ALL ->
            "Your library is empty."

        LibraryTab.FAVORITES ->
            "You haven't added any favorites yet."

        LibraryTab.HISTORY ->
            "You haven't read any stories yet."
    }

    Column(

        modifier = modifier.fillMaxWidth(),

        horizontalAlignment =
            Alignment.CenterHorizontally,

        verticalArrangement =
            Arrangement.Center

    ) {

        Text(
            text = message,
            style =
                MaterialTheme.typography.bodyLarge
        )
    }
}