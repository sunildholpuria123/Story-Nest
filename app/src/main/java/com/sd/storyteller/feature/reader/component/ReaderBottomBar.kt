package com.sd.storyteller.feature.reader.component

/**
 * Created by SDHOLPURIA on 05-08-2026.
 */

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.MusicOff
import androidx.compose.material.icons.outlined.Pause
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sd.storyteller.core.designsystem.theme.StoryNestPalette

@Composable
fun ReaderBottomBar(
    isFavorite: Boolean,
    musicEnabled: Boolean,
    isSpeaking: Boolean,
    isPaused: Boolean,
    ttsReady: Boolean,
    onFavorite: () -> Unit,
    onPlay: () -> Unit,
    onPause: () -> Unit,
    onStop: () -> Unit,
    onMusic: () -> Unit
) {

    val scrollState =
        rememberScrollState()

    Row(
        modifier =
            Modifier.horizontalScroll(
                scrollState
            ),
        horizontalArrangement =
            Arrangement.spacedBy(8.dp)
    ) {

        // -------------------------------------------------
        // Favorite
        // -------------------------------------------------

        FilledTonalButton(
            onClick = onFavorite
        ) {

            Icon(
                imageVector =
                    if (isFavorite) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Outlined.FavoriteBorder
                    },
                contentDescription =
                    if (isFavorite) {
                        "Remove from favorites"
                    } else {
                        "Add to favorites"
                    },
                tint =
                    StoryNestPalette.TextSecondary
            )

            Spacer(
                modifier =
                    Modifier.width(6.dp)
            )

            Text(
                text =
                    if (isFavorite) {
                        "Favorited"
                    } else {
                        "Favorite"
                    }
            )
        }

        // -------------------------------------------------
        // Read Aloud
        // -------------------------------------------------

        when {

            // =============================================
            // TTS Loading
            // =============================================

            !ttsReady -> {

                FilledTonalButton(
                    enabled = false,
                    onClick = {}
                ) {

                    CircularProgressIndicator(
                        modifier =
                            Modifier.width(18.dp),
                        strokeWidth = 2.dp
                    )

                    Spacer(
                        modifier =
                            Modifier.width(8.dp)
                    )

                    Text(
                        text =
                            "Preparing audio…"
                    )
                }
            }

            // =============================================
            // Speaking
            // =============================================

            isSpeaking -> {

                Button(
                    onClick =
                        onPause
                ) {

                    Icon(
                        imageVector =
                            Icons.Outlined.Pause,
                        contentDescription =
                            "Pause",
                        tint =
                            StoryNestPalette.TextSecondary
                    )

                    Spacer(
                        modifier =
                            Modifier.width(6.dp)
                    )

                    Text(
                        text =
                            "Pause"
                    )
                }
            }

            // =============================================
            // Paused
            // =============================================

            isPaused -> {

                Button(
                    onClick =
                        onPlay
                ) {

                    Icon(
                        imageVector =
                            Icons.Outlined.PlayArrow,
                        contentDescription =
                            "Resume",
                        tint =
                            StoryNestPalette.TextSecondary
                    )

                    Spacer(
                        modifier =
                            Modifier.width(6.dp)
                    )

                    Text(
                        text =
                            "Resume"
                    )
                }
            }

            // =============================================
            // Ready
            // =============================================

            else -> {

                Button(
                    onClick =
                        onPlay
                ) {

                    Icon(
                        imageVector =
                            Icons.Outlined.PlayArrow,
                        contentDescription =
                            "Read Aloud",
                        tint =
                            StoryNestPalette.TextSecondary
                    )

                    Spacer(
                        modifier =
                            Modifier.width(6.dp)
                    )

                    Text(
                        text =
                            "Read Aloud"
                    )
                }
            }
        }

        // -------------------------------------------------
        // Stop
        // -------------------------------------------------

        FilledTonalButton(
            enabled =
                isSpeaking || isPaused,
            onClick =
                onStop
        ) {

            Icon(
                imageVector =
                    Icons.Outlined.Stop,
                contentDescription =
                    "Stop",
                tint =
                    StoryNestPalette.TextSecondary
            )

            Spacer(
                modifier =
                    Modifier.width(6.dp)
            )

            Text(
                text =
                    "Stop"
            )
        }

        // -------------------------------------------------
        // Music
        // -------------------------------------------------

        /*FilledTonalButton(
            onClick =
                onMusic
        ) {

            Icon(
                imageVector =
                    if (musicEnabled) {
                        Icons.Filled.MusicNote
                    } else {
                        Icons.Outlined.MusicOff
                    },
                contentDescription =
                    if (musicEnabled) {
                        "Turn music off"
                    } else {
                        "Turn music on"
                    },
                tint =
                    StoryNestPalette.TextSecondary
            )

            Spacer(
                modifier =
                    Modifier.width(6.dp)
            )

            Text(
                text =
                    if (musicEnabled) {
                        "Music On"
                    } else {
                        "Music"
                    }
            )
        }*/
    }
}
