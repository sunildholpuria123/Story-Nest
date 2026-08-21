package com.sd.storyteller

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sd.storyteller.core.navigation.StoryNestNavHost

/**
 * Created by SDHOLPURIA on 31-07-2026.
 */

@Composable
fun StoryNestRoot() {

    Surface(
        modifier = Modifier
    ) {

        StoryNestNavHost()
    }
}