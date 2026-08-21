package com.sd.storyteller.core.audio

import android.content.Context
import android.media.MediaPlayer
import com.sd.storyteller.ui.theme.StoryTheme
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by SDHOLPURIA on 06-08-2026.
 */
@Singleton
class StoryAudioPlayer @Inject constructor(
    @ApplicationContext
    private val context: Context
) {

    private var mediaPlayer: MediaPlayer? = null

    private var currentTheme: StoryTheme? = null

    fun play(
        theme: StoryTheme
    ) {

        if (
            mediaPlayer?.isPlaying == true &&
            currentTheme == theme
        ) {
            return
        }

        stop()

        val resource =
            StoryMusic.resourceFor(theme)

        mediaPlayer =
            MediaPlayer.create(
                context,
                resource
            ).apply {

                isLooping = true

                setVolume(
                    0.25f,
                    0.25f
                )

                start()
            }

        currentTheme = theme
    }

    fun stop() {

        mediaPlayer?.stop()

        mediaPlayer?.release()

        mediaPlayer = null

        currentTheme = null
    }

    fun isPlaying(): Boolean {

        return mediaPlayer?.isPlaying == true
    }
}