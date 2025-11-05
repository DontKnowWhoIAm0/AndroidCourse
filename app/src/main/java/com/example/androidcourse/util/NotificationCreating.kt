package com.example.androidcourse.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput

import com.example.androidcourse.MainActivity
import com.example.androidcourse.R
import com.example.androidcourse.navigation.NavigationKeys
import com.example.androidcourse.ui.screen.NotificationViewModel

fun createNotification(context: Context, viewModel: NotificationViewModel) {
    val channelId = NotificationChannels.getChannelId(viewModel.selectedImportance, context)
    val notificationId = NotificationIDsController.getId();

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(viewModel.title)
        .setContentText(viewModel.text)
        .setPriority(viewModel.selectedImportance.getCompatPriority())
        .setAutoCancel(true)

    if (viewModel.expandable && viewModel.text.isNotEmpty()) {
        builder.setStyle(NotificationCompat.BigTextStyle().bigText(viewModel.text))
    }

    if (viewModel.openMainActivity) {
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra(NavigationKeys.TITLE, viewModel.title)
            putExtra(NavigationKeys.TEXT, viewModel.text)
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        builder.setContentIntent(pendingIntent)
    }

    if (viewModel.replyActionEnabled) {
        val remoteInput = RemoteInput.Builder(NavigationKeys.REPLY)
            .setLabel(context.getString(R.string.reply_label)).build()
        val replyIntent = Intent(context, ReplyReceiver::class.java).apply {
            putExtra(NavigationKeys.ID, notificationId)
        }

        val replyPendingIntent = PendingIntent.getBroadcast(
            context, notificationId, replyIntent,
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
    notificationManager.notify(notificationId, builder.build())
}

