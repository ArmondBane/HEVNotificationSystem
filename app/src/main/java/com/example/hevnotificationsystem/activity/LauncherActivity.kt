package com.example.hevnotificationsystem.activity

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import com.example.hevnotificationsystem.R
import com.example.receivers.battery.BatteryReceiver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi

@ExperimentalAnimationApi
@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {
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
                BatteryReceiver(),
                IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            )
        }
    }
}