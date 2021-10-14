package com.example.hevnotificationsystem.service

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import android.widget.RemoteViews
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.core.app.NotificationCompat
import com.example.core.device.manager.AudioManager
import com.example.hevnotificationsystem.R
import com.example.hevnotificationsystem.activity.MainActivity
import com.example.core.device.receiver.HEVReceiver
import com.example.core.device.receiver.battery.BatteryReceiver
import com.example.core.device.receiver.battery.ChargeReceiver
import com.example.core.device.receiver.mute.ToggleMuteReceiver
import java.util.*

@ExperimentalAnimationApi
class ReceiversService: Service() {

    private val audioManager = AudioManager()
    private var timer: Timer? = null
    private var wakeLock: PowerManager.WakeLock? = null

    private val receivers = mapOf<String, HEVReceiver>(
        BatteryReceiver.KEY to BatteryReceiver().also {
            BatteryReceiver.audioManager = audioManager
        },
        ChargeReceiver.KEY to ChargeReceiver().also {
            ChargeReceiver.audioManager = audioManager
        },
        ToggleMuteReceiver.KEY to ToggleMuteReceiver().also {
            ToggleMuteReceiver.audioManager = audioManager
        }
    )

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        if (isServiceRunning)
            audioManager.playSound(applicationContext, R.raw.power_restored)
        else
            audioManager.playSound(applicationContext, R.raw.activated)

        createNotificationChannel()
        showNotification()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        isServiceRunning = true

        wakeLock = (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
            newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "ReceiversService::lock").apply {
                acquire(24*60*60*1000L /*24 hours*/)
            }
        }

        startTimer()
        registerReceivers()

        return START_STICKY
    }

    private fun startTimer() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                Log.i(TAG, "running");
            }
        }, 1000, 1000)
    }

    private fun stopTimer() {
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
    }

    private fun registerReceivers() {
        receivers.forEach { receiverMap ->
            applicationContext.registerReceiver(
                receiverMap.value.broadcastReceiver,
                IntentFilter(receiverMap.value.action)
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        wakeLock?.apply {
            if (isHeld)
                release()
        }
        stopTimer()

        unregisterReceivers()

        if (isServiceRunning) {
            audioManager.playSound(applicationContext, R.raw.hev_shutdown)
            sendBroadcast(Intent(RESTART_ACTION))
        }
        else
            audioManager.playLastAndStop(applicationContext, R.raw.deactivated)
    }

    private fun unregisterReceivers() {
        receivers.forEach { receiverMap ->
            applicationContext.unregisterReceiver(receiverMap.value.broadcastReceiver)
        }
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

        private const val TAG = "ReceiversService"

        private const val STATUS_NOTIFICATION_ID = 923
        private const val STATUS_REQ_CODE = 955

        private const val CHANNEL_NAME = "ReceiversService"
        private const val CHANNEL_ID = "HEVServiceChannel"

        const val RESTART_ACTION = "com.example.hevnotificationsystem.service.Restart"

        var isServiceRunning = false
    }
}