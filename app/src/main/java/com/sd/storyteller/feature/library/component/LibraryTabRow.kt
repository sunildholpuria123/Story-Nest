package com.sd.storyteller.feature.library.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sd.storyteller.feature.library.ui.LibraryTab

/**
 * Created by SDHOLPURIA on 07-08-2026.
 */

@Composable
fun LibraryTabRow(

    selectedTab: LibraryTab,

    onTabSelected: (LibraryTab) -> Unit,

    modifier: Modifier = Modifier

) {

    Row(

        modifier = modifier.fillMaxWidth(),

        horizontalArrangement =
            Arrangement.spacedBy(8.dp)

    ) {

        LibraryTab.entries.forEach { tab ->

            FilterChip(

                selected =
                    selectedTab == tab,

                onClick = {
                    onTabSelected(tab)
                },

                label = {

                    Text(
                        text =
                            when (tab) {

                                LibraryTab.ALL ->
                                    "All"

                                LibraryTab.FAVORITES ->
                                    "Favorites"

                                LibraryTab.HISTORY ->
                                    "History"
                            }
                    )
                }
            )
        }
    }
}