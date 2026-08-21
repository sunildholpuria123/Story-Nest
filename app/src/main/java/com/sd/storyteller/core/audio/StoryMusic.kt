package com.sd.storyteller.core.audio

import com.sd.storyteller.R
import com.sd.storyteller.ui.theme.StoryTheme

/**
 * Maps a StoryTheme to its background music resource.
 *
 * Created by SDHOLPURIA on 08-08-2026.
 */
object StoryMusic {

    fun resourceFor(
        theme: StoryTheme
    ): Int {

        return when (theme) {

            StoryTheme.JUNGLE ->
                R.raw.jungle

            StoryTheme.OCEAN ->
                R.raw.ocean

            StoryTheme.SPACE ->
                R.raw.space

            StoryTheme.PRINCESS ->
                R.raw.princess

            StoryTheme.MAGIC ->
                R.raw.magic

            StoryTheme.ADVENTURE ->
                R.raw.adventure

            StoryTheme.ANIMALS ->
                R.raw.animals

            StoryTheme.BEDTIME ->
                R.raw.forest_night
        }
    }
}