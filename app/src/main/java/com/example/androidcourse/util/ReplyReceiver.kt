package com.example.androidcourse.util

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.RemoteInput
import com.example.androidcourse.data.UserMessages
import com.example.androidcourse.navigation.NavigationKeys

class ReplyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        val replyText = remoteInput?.getCharSequence(NavigationKeys.REPLY)?.toString()
        val notificationId = intent?.getIntExtra(NavigationKeys.ID, -1)

        if (!replyText.isNullOrEmpty()) {
            UserMessages.addMessage(replyText)
            if (notificationId != null && notificationId != -1) {
                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.cancel(notificationId)
            }
        }
    }
}