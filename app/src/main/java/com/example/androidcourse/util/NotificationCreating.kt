package com.example.androidcourse.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput

import com.example.androidcourse.MainActivity
import com.example.androidcourse.R
import com.example.androidcourse.data.Notification
import com.example.androidcourse.navigation.NavigationKeys

fun createNotification(context: Context, notification: Notification) {

    val channelId = NotificationChannels.getChannelId(notification.importance, context)

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(notification.title)
        .setContentText(notification.text)
        .setPriority(notification.importance.getPriority())
        .setAutoCancel(true)

    if (notification.expandable && notification.text.isNotEmpty()) {
        builder.setStyle(NotificationCompat.BigTextStyle().bigText(notification.text))
    }

    if (notification.openMainActivity) {
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra(NavigationKeys.TITLE, notification.title)
            putExtra(NavigationKeys.TEXT, notification.text)
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            notification.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        builder.setContentIntent(pendingIntent)
    }

    if (notification.replyActionEnabled) {
        val remoteInput = RemoteInput.Builder(NavigationKeys.REPLY)
            .setLabel(context.getString(R.string.reply_label)).build()
        val replyIntent = Intent(context, ReplyReceiver::class.java).apply {
            putExtra(NavigationKeys.ID, notification.id)
        }

        val replyPendingIntent = PendingIntent.getBroadcast(
            context, notification.id, replyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )

        val replyAction = NotificationCompat.Action.Builder(
            0,
            context.getString(R.string.reply_button),
            replyPendingIntent
        )
            .addRemoteInput(remoteInput).build()

        builder.addAction(replyAction)
    }

    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(notification.id, builder.build())
}

