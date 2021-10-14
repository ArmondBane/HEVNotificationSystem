package com.example.core.device.receiver.mute

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.core.R
import android.media.AudioManager as SystemAudioManager
import com.example.core.device.manager.AudioManager
import com.example.core.device.receiver.HEVReceiver

class ToggleMuteReceiver(
    override val receiverKey: String = KEY,
    override val action: String = VOLUME_CHANGED_ACTION
) : BroadcastReceiver(), HEVReceiver {

    override val broadcastReceiver: BroadcastReceiver = this

    override fun onReceive(context: Context?, intent: Intent?) {

        if (context == null || intent == null)
            return

        if (intent.action != VOLUME_CHANGED_ACTION)
            return

        val audioManager = audioManager ?: return
        val systemAudioManager =
            context.getSystemService(Context.AUDIO_SERVICE) as SystemAudioManager

        when (systemAudioManager.ringerMode) {
            SystemAudioManager.RINGER_MODE_NORMAL -> {
                if (isSystemWasMute) {
                    audioManager.playSound(context, R.raw.voice_on)
                    isSystemWasMute = false
                }
            }
            else -> {
                if (!isSystemWasMute) {
                    audioManager.playSound(context, R.raw.voice_off)
                    isSystemWasMute = true
                }
            }
        }
    }

    companion object {
        private var isSystemWasMute = true
        var audioManager: AudioManager? = null
        const val VOLUME_CHANGED_ACTION = "android.media.VOLUME_CHANGED_ACTION"
        const val KEY = "ToggleMuteReceiver"
    }
}