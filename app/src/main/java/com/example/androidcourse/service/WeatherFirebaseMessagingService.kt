package com.example.androidcourse.service

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
        PushNotificationKind.PROMO -> "Специальное предложение"
        PushNotificationKind.AUTH -> "Безопасность аккаунта"
        PushNotificationKind.WEATHER_ALERT -> "Погодное предупреждение"
        PushNotificationKind.UNKNOWN -> "Уведомление"
    }

    private fun defaultMessageForKind(kind: PushNotificationKind): String = when (kind) {
        PushNotificationKind.PROMO -> "У нас есть что-то для вас!"
        PushNotificationKind.AUTH -> "Выполнен вход в аккаунт"
        PushNotificationKind.WEATHER_ALERT -> "Проверьте погоду в вашем городе"
        PushNotificationKind.UNKNOWN -> "Новое сообщение"
    }
}