package com.example.receivers.battery

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import com.example.receivers.R
import com.example.receivers.util.SoundManager


class BatteryReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent == null || context == null)
            return

        val soundManager = SoundManager()
        val powerLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)

        if (powerLevel % 5 != 0 || powerLevel == 0)
            return

        soundManager.playSound(context, R.raw.power_level_is)
        when(powerLevel) {
            5 ->  soundManager.playSound(context, R.raw.five)
            10 ->  soundManager.playSound(context, R.raw.ten)
            15 ->  soundManager.playSound(context, R.raw.fifteen)
            20 ->  soundManager.playSound(context, R.raw.twenty)
            25 -> {
                soundManager.playSound(context, R.raw.twenty)
                soundManager.playSound(context, R.raw.five)
            }
            30 ->  soundManager.playSound(context, R.raw.thirty)
            35 -> {
                soundManager.playSound(context, R.raw.thirty)
                soundManager.playSound(context, R.raw.five)
            }
            40 ->  soundManager.playSound(context, R.raw.fourty)
            45 -> {
                soundManager.playSound(context, R.raw.fourty)
                soundManager.playSound(context, R.raw.five)
            }
            50 ->  soundManager.playSound(context, R.raw.fifty)
            55 -> {
                soundManager.playSound(context, R.raw.fifty)
                soundManager.playSound(context, R.raw.five)
            }
            60 ->  soundManager.playSound(context, R.raw.sixty)
            65 -> {
                soundManager.playSound(context, R.raw.sixty)
                soundManager.playSound(context, R.raw.five)
            }
            70 ->  soundManager.playSound(context, R.raw.seventy)
            75 -> {
                soundManager.playSound(context, R.raw.seventy)
                soundManager.playSound(context, R.raw.five)
            }
            80 ->  soundManager.playSound(context, R.raw.eighty)
            85 -> {
                soundManager.playSound(context, R.raw.eighty)
                soundManager.playSound(context, R.raw.five)
            }
            90 ->  soundManager.playSound(context, R.raw.ninety)
            95 -> {
                soundManager.playSound(context, R.raw.ninety)
                soundManager.playSound(context, R.raw.five)
            }
            100 ->  soundManager.playSound(context, R.raw.onehundred)
        }
        soundManager.playSound(context, R.raw.percent)
    }
}