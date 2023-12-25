package com.semdelion.data.repositories

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.semdelion.data.apis.notification.NotificationApi
import com.semdelion.domain.repositories.notification.models.PushNotification
import com.semdelion.data.core.apis.BaseApi
import com.semdelion.data.core.apis.client.ApiClient
import com.semdelion.domain.core.coroutines.IoDispatcher
import com.semdelion.domain.repositories.notification.INotificationRepository
import com.semdelion.domain.repositories.notification.models.PushNotificationLegacy
import javax.inject.Inject


class NotificationRepositoryImpl @Inject constructor(
    apiClient: ApiClient,
    private val dispatcher: IoDispatcher
): BaseApi(apiClient), INotificationRepository {

    private val notificationAPI = apiClient.createService(NotificationApi::class.java)

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun sendNotification(notification: PushNotification) = wrapRetrofitExceptions(dispatcher.value) {
        val response = notificationAPI.postNotification(notification)
        if(response.isSuccessful) {
            Log.d("sendNotification", "response: $response")
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun sendNotificationLegacy(notification: PushNotificationLegacy) = wrapRetrofitExceptions(dispatcher.value) {
        val response = notificationAPI.postNotificationLegacy(notification)
        if(response.isSuccessful) {
            Log.d("sendNotification", "response: $response")
        }
    }
}