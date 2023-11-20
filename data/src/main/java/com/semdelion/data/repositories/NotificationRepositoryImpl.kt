package com.semdelion.data.repositories

import android.os.Build
import androidx.annotation.RequiresExtension
import com.semdelion.data.apis.notification.NotificationApi
import com.semdelion.domain.repositories.notification.models.PushNotification
import com.semdelion.data.core.apis.BaseApi
import com.semdelion.data.core.apis.client.ApiClient
import com.semdelion.domain.core.coroutines.IoDispatcher
import com.semdelion.domain.repositories.notification.NotificationRepository

class NotificationRepositoryImpl(
    apiClient: ApiClient,
    private val dispatcher: IoDispatcher
): BaseApi(apiClient), NotificationRepository {

    val notificationAPI = apiClient.createService(NotificationApi::class.java)

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun sendNotification(notification: PushNotification) = wrapRetrofitExceptions(dispatcher.value) {
        val repository = notificationAPI.postNotification(notification)
        if(repository.isSuccessful) {

        }
    }
}