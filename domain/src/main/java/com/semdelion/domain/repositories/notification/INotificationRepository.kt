package com.semdelion.domain.repositories.notification

import com.semdelion.domain.repositories.notification.models.PushNotification
import com.semdelion.domain.repositories.notification.models.PushNotificationLegacy

interface INotificationRepository {
    suspend fun sendNotification(notification: PushNotification)

    suspend fun sendNotificationLegacy(notification: PushNotificationLegacy)
}