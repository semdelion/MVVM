package com.semdelion.data.apis.notification


import com.semdelion.domain.repositories.notification.models.PushNotification
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationApi {

    @Headers("Authorization: key=******", "Context-Type:application/json")
    @POST("https://fcm.googleapis.com/fcm/send")
    suspend fun postNotification(
        @Body notification: PushNotification
    ): Response<ResponseBody>
}