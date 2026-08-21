package com.sd.storyteller.core.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import com.sd.storyteller.core.constants.StoryLanguage
import com.sd.storyteller.core.util.StoryParser
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Controls story narration using Android TextToSpeech.
 *
 * Playback model:
 *
 * PLAY
 * ↓
 * Sentence 0
 * ↓
 * Sentence 1
 * ↓
 * Sentence 2
 * ↓
 * PAUSE
 * ↓
 * RESUME
 * ↓
 * Sentence 2
 * ↓
 * Sentence 3
 * ↓
 * COMPLETE
 *
 * Android TextToSpeech does not expose a true pause/resume API,
 * therefore pause/resume is implemented at sentence level.
 */
@Singleton
class StoryTextToSpeech @Inject constructor(
    @ApplicationContext context: Context
) : TextToSpeech.OnInitListener {

    // ---------------------------------------------------------
    // TTS
    // ---------------------------------------------------------

    private val tts =
        TextToSpeech(context.applicationContext, this)

    @Volatile
    private var ready = false

    // ---------------------------------------------------------
    // Language
    // ---------------------------------------------------------

    private var currentLocale: Locale =
        Locale.US
    private var currentSpeechStyle =
        SpeechStyle(
            speechRate = 0.9f,
            pitch = 1.1f
        )
    // ---------------------------------------------------------
    // Playback
    // ---------------------------------------------------------

    private var sentences: List<String> =
        emptyList()

    private var currentSentence = 0

    private var paused = false

    /**
     * Used to invalidate callbacks from
     * an old playback session.
     */
    private var playbackGeneration = 0L

    // ---------------------------------------------------------
    // Callbacks
    // ---------------------------------------------------------

    private var sentenceChanged:
            ((Int) -> Unit)? = null

    private var completed:
            (() -> Unit)? = null

    private var readyChanged:
            ((Boolean) -> Unit)? = null

    // ---------------------------------------------------------
    // Initialization
    // ---------------------------------------------------------

    init {

        tts.setOnUtteranceProgressListener(
            object : UtteranceProgressListener() {

                override fun onStart(
                    utteranceId: String?
                ) = Unit

                override fun onError(
                    utteranceId: String?
                ) {
                    // Do not change UI state here.
                    // ViewModel remains the source of UI state.
                }

                override fun onDone(
                    utteranceId: String?
                ) {

                    if (paused) {
                        return
                    }

                    val id =
                        utteranceId
                            ?: return

                    if (!id.startsWith("story_")) {
                        return
                    }

                    val parts =
                        id.split("_")

                    if (parts.size != 3) {
                        return
                    }

                    val generation =
                        parts[1].toLongOrNull()
                            ?: return

                    val sentenceIndex =
                        parts[2].toIntOrNull()
                            ?: return

                    // Ignore callbacks from old playback.
                    if (
                        generation !=
                        playbackGeneration
                    ) {
                        return
                    }

                    // Ignore unexpected callbacks.
                    if (
                        sentenceIndex !=
                        currentSentence
                    ) {
                        return
                    }

                    currentSentence++

                    if (
                        currentSentence >=
                        sentences.size
                    ) {

                        completed?.invoke()

                        return
                    }

                    speakCurrentSentence()
                }
            }
        )
    }

    fun setSpeechStyle(
        style: SpeechStyle
    ) {

        currentSpeechStyle =
            style

        if (!ready) {
            return
        }

        tts.setSpeechRate(
            style.speechRate
        )

        tts.setPitch(
            style.pitch
        )
    }
    // ---------------------------------------------------------
    // Callbacks
    // ---------------------------------------------------------

    fun setOnSentenceChanged(
        callback: (Int) -> Unit
    ) {
        sentenceChanged = callback
    }

    fun setOnCompleted(
        callback: () -> Unit
    ) {
        completed = callback
    }

    fun setOnReadyChanged(
        callback: (Boolean) -> Unit
    ) {

        readyChanged = callback

        // Immediately send current state.
        callback(ready)
    }

    fun getSentenceCount(): Int {
        return sentences.size
    }

    fun isReady(): Boolean {
        return ready
    }

    // ---------------------------------------------------------
    // TTS Initialization
    // ---------------------------------------------------------

    override fun onInit(
        status: Int
    ) {

        if (
            status !=
            TextToSpeech.SUCCESS
        ) {

            updateReady(false)

            return
        }

        configureLanguage(
            currentLocale
        )
    }

    // ---------------------------------------------------------
    // Language
    // ---------------------------------------------------------

    /**
     * Changes the TTS language.
     *
     * Example:
     *
     * Locale("hi", "IN")
     * Locale.US
     * Locale.UK
     */
    fun setLanguage(
        locale: Locale
    ): Boolean {

        currentLocale = locale

        if (!ready) {
            return false
        }

        return configureLanguage(locale)
    }

    /**
     * Configures Android TTS with the
     * requested locale.
     */
    private fun configureLanguage(
        locale: Locale
    ): Boolean {

        val result =
            tts.setLanguage(locale)

        val languageSupported =
            result !=
                    TextToSpeech.LANG_MISSING_DATA &&
                    result !=
                    TextToSpeech.LANG_NOT_SUPPORTED

        if (languageSupported) {

            tts.setSpeechRate(
                currentSpeechStyle.speechRate
            )

            tts.setPitch(
                currentSpeechStyle.pitch
            )
        }

        updateReady(
            languageSupported
        )

        return languageSupported
    }

    // ---------------------------------------------------------
    // Ready State
    // ---------------------------------------------------------

    private fun updateReady(
        value: Boolean
    ) {

        ready = value

        readyChanged?.invoke(
            value
        )
    }

    // ---------------------------------------------------------
    // Play
    // ---------------------------------------------------------

    fun play(
        text: String,
        language: StoryLanguage,
        startSentence: Int = 0
    ): Boolean {
        val locale = Locale.forLanguageTag(
            language.code
        )
        if (!setLanguage(locale)) {
            return false
        }

        if (!ready) {
            return false
        }

        if (text.isBlank()) {
            return false
        }

        val parsedSentences =
            StoryParser.splitIntoSentences(text, language)

        if (parsedSentences.isEmpty()) {
            return false
        }

        playbackGeneration++

        tts.stop()

        sentences = parsedSentences

        currentSentence =
            startSentence.coerceIn(
                0,
                sentences.lastIndex
            )

        paused = false

        speakCurrentSentence()

        return true
    }

    // ---------------------------------------------------------
    // Pause
    // ---------------------------------------------------------

    fun pause() {

        if (!ready) {
            return
        }

        if (sentences.isEmpty()) {
            return
        }

        if (
            currentSentence >=
            sentences.size
        ) {
            return
        }

        paused = true

        tts.stop()
    }

    // ---------------------------------------------------------
    // Resume
    // ---------------------------------------------------------

    fun resume(): Boolean {

        if (
            !ready ||
            sentences.isEmpty()
        ) {
            return false
        }

        paused = false

        speakCurrentSentence()

        return true
    }

    // ---------------------------------------------------------
    // Stop
    // ---------------------------------------------------------

    fun stop() {

        playbackGeneration++

        paused = false

        currentSentence = 0

        sentences = emptyList()

        tts.stop()
    }

    // ---------------------------------------------------------
    // Current Sentence
    // ---------------------------------------------------------

    private fun speakCurrentSentence() {

        if (!ready) {
            return
        }

        if (paused) {
            return
        }

        if (
            currentSentence >=
            sentences.size
        ) {

            completed?.invoke()

            return
        }

        sentenceChanged?.invoke(
            currentSentence
        )

        val utteranceId =
            "story_${playbackGeneration}_${currentSentence}"

        tts.speak(
            sentences[currentSentence],
            TextToSpeech.QUEUE_FLUSH,
            null,
            utteranceId
        )
    }

    // ---------------------------------------------------------
    // Shutdown
    // ---------------------------------------------------------

    fun shutdown() {

        playbackGeneration++

        paused = false

        sentences = emptyList()

        currentSentence = 0

        tts.stop()

        tts.shutdown()

        updateReady(false)
    }
}