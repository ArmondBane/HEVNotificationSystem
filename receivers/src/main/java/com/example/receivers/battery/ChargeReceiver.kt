package com.example.receivers.battery

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import com.example.receivers.R
import com.example.receivers.util.SoundManager

class ChargeReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent == null || context == null)
            return

        val soundManager = SoundManager()

        val plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)

        if (plugged == BatteryManager.BATTERY_PLUGGED_AC)
            soundManager.playSound(context, R.raw.automedic_on)

        if (plugged == BatteryManager.BATTERY_PLUGGED_USB)
            soundManager.playSound(context, R.raw.evacuate_area)

        if (plugged == BatteryManager.BATTERY_PLUGGED_WIRELESS)
            soundManager.playSound(context, R.raw.targetting_system)
    }
}