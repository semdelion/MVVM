package com.semdelion.domain.repositories.notification

import com.semdelion.domain.repositories.notification.models.PushNotification

interface NotificationRepository {
    suspend fun sendNotification(notification: PushNotification)
}