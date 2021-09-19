package com.example.core.util

import android.content.Context
import android.media.MediaPlayer

class AudioManager {

    private var mediaPlayer: MediaPlayer? = null

    private val playerStack = mutableListOf<Int>()

    fun playSound(context: Context, rawId: Int) {
        if (playerStack.isEmpty()) {
            playerStack.add(rawId)
            playNextSound(context)
        } else
            playerStack.add(rawId)
    }

    private fun playNextSound(context: Context) {
        mediaPlayer = MediaPlayer.create(context, playerStack.first())
        mediaPlayer!!.setOnCompletionListener {
            playerStack.removeFirst()
            if (playerStack.isNotEmpty())
                playNextSound(context)
        }
        mediaPlayer!!.isLooping = false
        mediaPlayer!!.start()
    }

    fun pauseSound() {
        if (mediaPlayer != null && mediaPlayer!!.isPlaying)
            mediaPlayer!!.pause()
    }

    fun playLastAndStop(context: Context, lastRawId: Int) {
        if (mediaPlayer != null)
            mediaPlayer!!.stop()
        mediaPlayer = MediaPlayer.create(context, lastRawId)
        mediaPlayer!!.setOnCompletionListener {
            stopSound()
        }
        mediaPlayer!!.isLooping = false
        mediaPlayer!!.start()
    }

    private fun stopSound() {
        if (mediaPlayer != null) {
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
            mediaPlayer = null
        }
    }
}