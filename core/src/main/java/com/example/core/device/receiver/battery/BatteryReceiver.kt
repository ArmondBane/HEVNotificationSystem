package com.example.core.device.receiver.battery

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import com.example.core.R
import com.example.core.device.manager.AudioManager
import com.example.core.device.receiver.HEVReceiver


class BatteryReceiver(
    override val receiverKey: String = KEY,
    override val action: String = Intent.ACTION_BATTERY_CHANGED,
) : BroadcastReceiver(), HEVReceiver {

    override val broadcastReceiver: BroadcastReceiver = this

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent == null || context == null)
            return

        if (intent.action != Intent.ACTION_BATTERY_CHANGED)
            return

        val audioManager = audioManager ?: return
        val powerLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)

        if (powerLevel % 5 != 0 || powerLevel == 0)
            return

        audioManager.playSound(context, R.raw.power_level_is)
        when(powerLevel) {
            5 ->  audioManager.playSound(context, R.raw.five)
            10 ->  audioManager.playSound(context, R.raw.ten)
            15 ->  audioManager.playSound(context, R.raw.fifteen)
            20 ->  audioManager.playSound(context, R.raw.twenty)
            25 -> {
                audioManager.playSound(context, R.raw.twenty)
                audioManager.playSound(context, R.raw.five)
            }
            30 ->  audioManager.playSound(context, R.raw.thirty)
            35 -> {
                audioManager.playSound(context, R.raw.thirty)
                audioManager.playSound(context, R.raw.five)
            }
            40 ->  audioManager.playSound(context, R.raw.fourty)
            45 -> {
                audioManager.playSound(context, R.raw.fourty)
                audioManager.playSound(context, R.raw.five)
            }
            50 ->  audioManager.playSound(context, R.raw.fifty)
            55 -> {
                audioManager.playSound(context, R.raw.fifty)
                audioManager.playSound(context, R.raw.five)
            }
            60 ->  audioManager.playSound(context, R.raw.sixty)
            65 -> {
                audioManager.playSound(context, R.raw.sixty)
                audioManager.playSound(context, R.raw.five)
            }
            70 ->  audioManager.playSound(context, R.raw.seventy)
            75 -> {
                audioManager.playSound(context, R.raw.seventy)
                audioManager.playSound(context, R.raw.five)
            }
            80 ->  audioManager.playSound(context, R.raw.eighty)
            85 -> {
                audioManager.playSound(context, R.raw.eighty)
                audioManager.playSound(context, R.raw.five)
            }
            90 ->  audioManager.playSound(context, R.raw.ninety)
            95 -> {
                audioManager.playSound(context, R.raw.ninety)
                audioManager.playSound(context, R.raw.five)
            }
            100 ->  audioManager.playSound(context, R.raw.onehundred)
        }
        audioManager.playSound(context, R.raw.percent)
    }

    companion object {
        var audioManager: AudioManager? = null
        const val KEY = "BatteryReceiver"
    }
}