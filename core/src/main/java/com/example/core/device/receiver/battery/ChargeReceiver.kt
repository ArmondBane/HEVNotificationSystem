package com.example.core.device.receiver.battery

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import com.example.core.R
import com.example.core.device.manager.AudioManager
import com.example.core.device.receiver.HEVReceiver

class ChargeReceiver(
    override val receiverKey: String = KEY,
    override val action: String = Intent.ACTION_BATTERY_CHANGED
)  : BroadcastReceiver(), HEVReceiver {

    override val broadcastReceiver: BroadcastReceiver = this

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent == null || context == null)
            return

        if (intent.action != Intent.ACTION_BATTERY_CHANGED)
            return

        val audioManager = audioManager ?: return

        val plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)

        if (plugged == BatteryManager.BATTERY_PLUGGED_AC)
            audioManager.playSound(context, R.raw.automedic_on)

        if (plugged == BatteryManager.BATTERY_PLUGGED_USB)
            audioManager.playSound(context, R.raw.evacuate_area)

        if (plugged == BatteryManager.BATTERY_PLUGGED_WIRELESS)
            audioManager.playSound(context, R.raw.targetting_system)
    }

    companion object {
        var audioManager: AudioManager? = null
        const val KEY = "ChargeReceiver"
    }
}