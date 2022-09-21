package com.udacity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat

// Notification ID.
private val NOTIFICATION_ID = 0


fun NotificationManager.sendNotification(
    messageBody: String,
    status: String,
    applicationContext: Context
) {
    // Create the content intent for the notification, which launches
    // this activity
    val contentIntent = Intent(applicationContext, DetailActivity::class.java)
    contentIntent.putExtra("title", messageBody)
    contentIntent.putExtra("status", status)
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    // Build the notification
    val builder = NotificationCompat.Builder(
        applicationContext,
        "123456"
    )
        .setSmallIcon(R.drawable.ic_assistant_black_24dp)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(messageBody)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
    builder.addAction(R.drawable.ic_assistant_black_24dp, "Details", contentPendingIntent)

    Log.e("TAG", "sendNotification: ")
    notify(NOTIFICATION_ID, builder.build())
}

/**
 * Cancels all notifications.
 */


fun createDownloadNotificationChannel(context: Context) {
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is new and not in the support library
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "download"
        val descriptionText ="description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("123456", name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}
