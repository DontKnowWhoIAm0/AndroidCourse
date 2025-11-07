package com.example.androidcourse.data

data class Notification(
    var id: Int,
    var title: String,
    var text: String,
    var importance: Importance,
    var expandable: Boolean,
    var openMainActivity: Boolean,
    var replyActionEnabled: Boolean,
)

object NotificationIDsController {
    private var id = 1;
    private val notifications = mutableMapOf<Int, Notification>()

    fun add(notification: Notification) {
        notifications[notification.id] = notification
    }

    fun edit(notification: Notification) {
        notifications[notification.id] = notification
    }

    fun get(notificationId: Int): Notification? = notifications[notificationId]

    fun getId(): Int = id++
}