package com.example.androidcourse.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.androidcourse.R
import com.example.androidcourse.data.Importance

object NotificationChannels {

    private val channels = mutableSetOf<String>()

    fun getChannelId(importance: Importance, context: Context): String {
        val channelId = importance.getChannelId()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!channels.contains(channelId)) {
                createChannel(importance, channelId, context)
                channels.add(channelId)
            }
        }

        return channelId
    }

    private fun createChannel(importance: Importance, channelId: String, context: Context) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (manager.getNotificationChannel(channelId) == null) {
                val channelName = when (importance) {
                    Importance.LOW -> context.getString(R.string.low_imp)
                    Importance.MEDIUM -> context.getString(R.string.medium_imp)
                    Importance.HIGH -> context.getString(R.string.high_imp)
                    Importance.URGENT -> context.getString(R.string.urgent_imp)
                }

                val importanceLevel = importance.getImportance()

                val channel = NotificationChannel(
                    channelId,
                    channelName,
                    importanceLevel
                )
                manager.createNotificationChannel(channel)
            }
        }
    }
}