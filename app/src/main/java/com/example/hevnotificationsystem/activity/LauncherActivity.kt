package com.example.hevnotificationsystem.activity

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import com.example.hevnotificationsystem.R
import com.example.receivers.battery.BatteryReceiver
import com.example.receivers.mute.ToggleMuteReceiver
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {

    private val batteryReceiver = BatteryReceiver()
    private val toggleMuteReceiver = ToggleMuteReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_HEVNotificationSystem_Launcher)
        super.onCreate(savedInstanceState)

        setSettings()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setSettings() {
        applicationContext.apply {
            registerReceiver(
                batteryReceiver,
                IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            )
            registerReceiver(
                toggleMuteReceiver,
                IntentFilter(VOLUME_CHANGED_ACTION)
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        applicationContext.apply {
            unregisterReceiver(batteryReceiver)
            unregisterReceiver(toggleMuteReceiver)
        }
    }

    companion object {
        const val VOLUME_CHANGED_ACTION = "android.media.VOLUME_CHANGED_ACTION"
    }
}