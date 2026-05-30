package com.example.androidcourse.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.androidcourse.R

object NotificationHelper {

    private const val CHANNEL_PROMO = "channel_promo"
    private const val CHANNEL_AUTH = "channel_auth"
    private const val CHANNEL_WEATHER = "channel_weather"
    private const val CHANNEL_DEFAULT = "channel_default"

    fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = context.getSystemService(NotificationManager::class.java)
            listOf(
                NotificationChannel(CHANNEL_PROMO, context.getString(R.string.channel_promo_name), NotificationManager.IMPORTANCE_LOW),
                NotificationChannel(CHANNEL_AUTH, context.getString(R.string.channel_auth_name), NotificationManager.IMPORTANCE_HIGH),
                NotificationChannel(CHANNEL_WEATHER, context.getString(R.string.channel_weather_name), NotificationManager.IMPORTANCE_DEFAULT),
                NotificationChannel(CHANNEL_DEFAULT, context.getString(R.string.channel_default_name), NotificationManager.IMPORTANCE_DEFAULT)
            ).forEach { manager.createNotificationChannel(it) }
        }
    }

    fun showNotification(
        context: Context,
        kind: PushNotificationKind,
        title: String,
        message: String
    ) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val (channelId, icon) = when (kind) {
            PushNotificationKind.PROMO -> CHANNEL_PROMO to R.drawable.ic_launcher_foreground
            PushNotificationKind.AUTH -> CHANNEL_AUTH to R.drawable.ic_launcher_foreground
            PushNotificationKind.WEATHER_ALERT -> CHANNEL_WEATHER to R.drawable.ic_launcher_foreground
            PushNotificationKind.UNKNOWN -> CHANNEL_DEFAULT to R.drawable.ic_launcher_foreground
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setAutoCancel(true)
            .setPriority(
                when (kind) {
                    PushNotificationKind.AUTH,
                    PushNotificationKind.WEATHER_ALERT -> NotificationCompat.PRIORITY_HIGH
                    else -> NotificationCompat.PRIORITY_DEFAULT
                }
            )
            .build()

        manager.notify(System.currentTimeMillis().toInt(), notification)
    }
}