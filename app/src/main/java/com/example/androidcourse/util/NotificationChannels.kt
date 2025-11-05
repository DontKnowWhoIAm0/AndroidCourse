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
        val channelId = importance.getDisplayNameId().toString()

        if (!channels.contains(channelId)) {
            createChannel(importance, channelId, context)
            channels.add(channelId)
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
                    Importance.MAX -> context.getString(R.string.max_imp)
                }

                val importanceLevel = importance.getCompatPriority()

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