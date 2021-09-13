package com.example.receivers.mute

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import com.example.receivers.R
import com.example.receivers.util.SoundManager


class ToggleMuteReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (context == null)
            return

        val soundManager = SoundManager()
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        when (audioManager.ringerMode) {
            AudioManager.RINGER_MODE_NORMAL -> soundManager.playSound(context, R.raw.voice_on)
            AudioManager.RINGER_MODE_SILENT -> soundManager.playSound(context, R.raw.voice_off)
            AudioManager.RINGER_MODE_VIBRATE -> soundManager.playSound(context, R.raw.voice_off)
        }
    }
}