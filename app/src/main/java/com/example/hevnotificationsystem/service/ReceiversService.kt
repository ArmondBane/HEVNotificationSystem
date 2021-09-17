package com.example.hevnotificationsystem.service

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.core.app.NotificationCompat
import com.example.core.util.AudioManager
import com.example.hevnotificationsystem.R
import com.example.hevnotificationsystem.activity.MainActivity
import com.example.hevnotificationsystem.receiver.battery.BatteryReceiver
import com.example.hevnotificationsystem.receiver.battery.ChargeReceiver
import com.example.hevnotificationsystem.receiver.mute.ToggleMuteReceiver

@ExperimentalAnimationApi
class ReceiversService: Service() {

    private val audioManager = AudioManager()

    private val batteryReceiver = BatteryReceiver().also {
        BatteryReceiver.audioManager = audioManager
    }
    private val chargeReceiver = ChargeReceiver().also {
        ChargeReceiver.audioManager = audioManager
    }
    private val toggleMuteReceiver = ToggleMuteReceiver().also {
        ToggleMuteReceiver.audioManager = audioManager
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        registerReceivers()
        createNotificationChannel()
        showNotification()

        audioManager.playSound(applicationContext, R.raw.activated)

        return START_STICKY
    }

    private fun registerReceivers() {
        applicationContext.apply {
            registerReceiver(
                batteryReceiver,
                IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            )
            registerReceiver(
                chargeReceiver,
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
            unregisterReceiver(chargeReceiver)
            unregisterReceiver(toggleMuteReceiver)
        }

        audioManager.playLastAndStop(applicationContext, R.raw.deactivated)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                enableVibration(true)
                enableLights(true)
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun showNotification() {
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            action = Intent.ACTION_MAIN
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        val pendingIntent = if (Build.VERSION.SDK_INT >= 23)
            PendingIntent.getActivity(
                applicationContext,
                STATUS_REQ_CODE,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        else
            PendingIntent.getActivity(applicationContext, STATUS_REQ_CODE, intent, 0)

        val notificationBuild = NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            setSmallIcon(R.drawable.ic_half_life_lambda)
            setContentIntent(pendingIntent)
            setOngoing(true)
            setContent(RemoteViews(packageName, R.layout.status_notification))
        }.build()

        startForeground(STATUS_NOTIFICATION_ID, notificationBuild)
    }

    companion object {
        private const val STATUS_NOTIFICATION_ID = 923
        private const val STATUS_REQ_CODE = 955

        private const val CHANNEL_NAME = "ReceiversService"
        private const val CHANNEL_ID = "HEVServiceChannel"

        const val VOLUME_CHANGED_ACTION = "android.media.VOLUME_CHANGED_ACTION"
    }
}