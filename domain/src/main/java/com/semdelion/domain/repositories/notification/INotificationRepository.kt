package com.semdelion.domain.repositories.notification

import com.semdelion.domain.repositories.notification.models.PushNotification

interface INotificationRepository {
    suspend fun sendNotification(notification: PushNotification)
}