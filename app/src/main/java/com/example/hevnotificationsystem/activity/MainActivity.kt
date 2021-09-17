package com.example.hevnotificationsystem.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.example.hevnotificationsystem.service.ReceiversService
import com.example.hevnotificationsystem.ui.navigation.Navigation
import com.example.hevnotificationsystem.ui.theme.HEVNotificationSystemTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSettings()
        setContent {
            HEVNotificationSystemTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Navigation(exit = { exitApplication() })
                }
            }
        }
    }

    private fun setSettings() {
        startReceiversService()
    }

    private fun startReceiversService() {
        val serviceIntent = Intent(applicationContext, ReceiversService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            applicationContext.startForegroundService(serviceIntent)
        else
            applicationContext.startService(serviceIntent)
    }

    private fun exitApplication() {
        val serviceIntent = Intent(applicationContext, ReceiversService::class.java)
        stopService(serviceIntent)
        finish()
    }
}