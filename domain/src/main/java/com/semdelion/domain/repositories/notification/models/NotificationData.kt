package com.semdelion.domain.repositories.notification.models

data class NotificationData(
    val title: String,
    val message: String
)

data class NotificationMessage(
    val token: String,
    val data: NotificationData,
)

data class PushNotification(
    val message: NotificationMessage
)

data class PushNotificationLegacy(
    val to: String,
    val data: NotificationData
)