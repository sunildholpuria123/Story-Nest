package com.sd.storyteller.feature.reader.component

/**
 * Created by SDHOLPURIA on 05-08-2026.
 */

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sd.storyteller.core.constants.StoryLanguage
import com.sd.storyteller.core.designsystem.theme.StoryNestPalette
import com.sd.storyteller.core.util.StoryParser

@Composable
fun StoryContent(
    content: String,
    currentSentence: Int,
    isSpeaking: Boolean,
    language: StoryLanguage
) {

    // ---------------------------------------------------------
    // Parse story into sentences
    // ---------------------------------------------------------

    val sentences = remember(
        content,
        language
    ) {

        StoryParser.splitIntoSentences(
            story = content,
            language = language
        )
    }

    // ---------------------------------------------------------
    // List state
    // ---------------------------------------------------------

    val listState =
        rememberLazyListState()

    // ---------------------------------------------------------
    // Automatically follow the spoken sentence
    // ---------------------------------------------------------

    LaunchedEffect(
        currentSentence,
        isSpeaking
    ) {

        if (
            isSpeaking &&
            currentSentence >= 0 &&
            currentSentence < sentences.size
        ) {

            listState.animateScrollToItem(
                index = currentSentence,
                scrollOffset = -80
            )
        }
    }

    // ---------------------------------------------------------
    // Story
    // ---------------------------------------------------------

    LazyColumn(

        modifier =
            Modifier.fillMaxWidth(),

        state =
            listState,

        contentPadding =
            PaddingValues(
                vertical = 8.dp
            )
    ) {

        itemsIndexed(

            items = sentences,

            key = { index, _ ->
                index
            }

        ) { index, sentence ->

            val isCurrentSentence =
                index == currentSentence

            // -------------------------------------------------
            // Animated background
            // -------------------------------------------------

            val backgroundColor by
            animateColorAsState(

                targetValue =
                    if (
                        isCurrentSentence &&
                        isSpeaking
                    ) {

                        MaterialTheme
                            .colorScheme
                            .primary
                            .copy(
                                alpha = 0.15f
                            )

                    } else {

                        Color.Transparent
                    },

                label =
                    "sentenceBackground"
            )

            // -------------------------------------------------
            // Animated text color
            // -------------------------------------------------

            val textColor by
            animateColorAsState(

                targetValue =
                    if (
                        isCurrentSentence &&
                        isSpeaking
                    ) {

                        StoryNestPalette
                            .TextPrimary

                    } else {

                        StoryNestPalette
                            .TextSecondary
                    },

                label =
                    "sentenceTextColor"
            )

            // -------------------------------------------------
            // Sentence
            // -------------------------------------------------

            Text(

                text =
                    sentence,

                color =
                    textColor,

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                12.dp
                            )
                        )
                        .background(
                            backgroundColor
                        )
                        .padding(
                            12.dp
                        ),

                style =
                    MaterialTheme
                        .typography
                        .bodyLarge
            )
        }
    }
}
