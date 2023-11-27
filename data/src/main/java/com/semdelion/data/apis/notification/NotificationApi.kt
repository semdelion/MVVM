package com.semdelion.data.apis.notification


import com.semdelion.domain.repositories.notification.models.PushNotification
import com.semdelion.domain.repositories.notification.models.PushNotificationLegacy
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface NotificationApi {

    @Headers("Authorization: key=*******", "Context-Type:application/json")
    @POST("https://fcm.googleapis.com/fcm/send")
    suspend fun postNotificationLegacy(
        @Body notification: PushNotificationLegacy
    ): Response<ResponseBody>

    @Headers("Authorization: Bearer *******", "Context-Type:application/json")
    @POST("https://fcm.googleapis.com/v1/projects/{projectName}/messages:send")
    suspend fun postNotification(
        @Body notification: PushNotification,
        @Path("projectName") projectName: String = "semdelionmvvm"
    ): Response<ResponseBody>
}