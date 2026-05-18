package com.example.androidcourse.service

import com.example.androidcourse.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.crashlytics.FirebaseCrashlytics

class WeatherFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        FirebaseCrashlytics.getInstance().log("FCM token updated: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val data = message.data
        FirebaseCrashlytics.getInstance().log("Push received, data: $data")

        val kind = PushNotificationKind.fromValue(data["kind"])
        val title = data["title"] ?: defaultTitleForKind(kind)
        val body = data["message"] ?: defaultMessageForKind(kind)

        NotificationHelper.showNotification(
            context = applicationContext,
            kind = kind,
            title = title,
            message = body
        )
    }

    private fun defaultTitleForKind(kind: PushNotificationKind): String = when (kind) {
        PushNotificationKind.PROMO -> getString(R.string.push_title_promo)
        PushNotificationKind.AUTH -> getString(R.string.push_title_auth)
        PushNotificationKind.WEATHER_ALERT -> getString(R.string.push_title_weather)
        PushNotificationKind.UNKNOWN -> getString(R.string.push_title_unknown)
    }

    private fun defaultMessageForKind(kind: PushNotificationKind): String = when (kind) {
        PushNotificationKind.PROMO -> getString(R.string.push_message_promo)
        PushNotificationKind.AUTH -> getString(R.string.push_message_auth)
        PushNotificationKind.WEATHER_ALERT -> getString(R.string.push_message_weather)
        PushNotificationKind.UNKNOWN -> getString(R.string.push_message_unknown)
    }
}