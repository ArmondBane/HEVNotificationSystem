package com.example.hevnotificationsystem.receiver.mute

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import android.media.AudioManager as SystemAudioManager
import com.example.core.util.AudioManager
import com.example.hevnotificationsystem.R
import com.example.hevnotificationsystem.service.ReceiversService.Companion.VOLUME_CHANGED_ACTION

@ExperimentalAnimationApi
class ToggleMuteReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (context == null || intent == null)
            return

        if (intent.action != VOLUME_CHANGED_ACTION)
            return

        val audioManager = audioManager ?: return
        val systemAudioManager =
            context.getSystemService(Context.AUDIO_SERVICE) as SystemAudioManager

        when (systemAudioManager.ringerMode) {
            SystemAudioManager.RINGER_MODE_NORMAL ->
                audioManager.playSound(context, R.raw.voice_on)
            SystemAudioManager.RINGER_MODE_SILENT ->
                audioManager.playSound(context, R.raw.voice_off)
            SystemAudioManager.RINGER_MODE_VIBRATE ->
                audioManager.playSound(context, R.raw.voice_off)
        }
    }

    companion object {
        var audioManager: AudioManager? = null
    }
}