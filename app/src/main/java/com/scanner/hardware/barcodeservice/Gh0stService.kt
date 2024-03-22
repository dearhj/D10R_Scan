package com.scanner.hardware.barcodeservice

import android.app.ActivityManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.IBinder
import android.system.Os
import androidx.core.app.NotificationCompat
import com.github.h4de5ing.baseui.logD
import com.scanner.hardware.R
import com.scanner.hardware.util.isDebug
import java.lang.reflect.Method

class Gh0stService : Service() {
    override fun onBind(p0: Intent?): IBinder? = null
    private var serviceIntent: Intent? = null
    override fun onCreate() {
        super.onCreate()
        val notificationManager = this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val id = "gh0st_id"
        val title =
            if (isDebug()) getString(R.string.app_name) else getString(R.string.service_running)
        val mChannel = NotificationChannel(id, id, NotificationManager.IMPORTANCE_NONE)
        notificationManager.createNotificationChannel(mChannel)
        val notification = Notification.Builder(this, id).setOngoing(true).setContentTitle(title)
            .setSmallIcon(R.drawable.baseline_android_24).setColor(
                Color.argb(255, 0, 37, 150)
            ).setCategory(NotificationCompat.CATEGORY_SERVICE).build()
        startForeground(1, notification)
        serviceIntent = Intent(this, SerialPortService::class.java)
        startForegroundService(serviceIntent)
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_USER_FOREGROUND)
        intentFilter.addAction(Intent.ACTION_USER_BACKGROUND)
        registerReceiver(receiver, intentFilter)
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            intent.action?.apply {
                val uid = Os.getuid() / 100000
                "$uid $this".logD()
                when (this) {
                    Intent.ACTION_USER_FOREGROUND -> {}
                    Intent.ACTION_USER_BACKGROUND -> {
                        killAssignPkg(packageName)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceIntent?.apply { stopService(serviceIntent) }
        unregisterReceiver(receiver)
    }

    private fun killAssignPkg(packageName: String) {
        val mActivityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val method: Method?
        try {
            method = Class.forName("android.app.ActivityManager")
                .getMethod("forceStopPackage", String::class.java)
            method.invoke(mActivityManager, packageName)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}