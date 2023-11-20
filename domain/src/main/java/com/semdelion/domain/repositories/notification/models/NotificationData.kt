package com.semdelion.domain.repositories.notification.models

data class NotificationData(
    val title: String,
    val message: String
)
data class PushNotification(
    val data: NotificationData,
    val to: String
)
