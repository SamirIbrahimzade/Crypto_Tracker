package com.task.cryptotracker.util

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class NotificationManager @Inject constructor(val context: Context) {

    private val channelId = "crypto_tracker_notification_channel"
    private val channelName: CharSequence = "Crypto Tracker Notification"
    private val importance = NotificationManager.IMPORTANCE_DEFAULT
    private val channel = NotificationChannel(channelId, channelName, importance)
    private val notificationManager: NotificationManager? =
        ContextCompat.getSystemService(context, NotificationManager::class.java)

    fun sendDefaultNotification(title: String, content: String) {
        notificationManager?.createNotificationChannel(channel)

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationId = 1
        notificationManager?.notify(notificationId, builder.build())
    }

}