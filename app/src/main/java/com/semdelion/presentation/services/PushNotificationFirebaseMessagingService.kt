package com.semdelion.presentation.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.semdelion.presentation.R
import com.semdelion.presentation.RoutingActivity
import okhttp3.internal.notify
import kotlin.random.Random

private const val CHANNEL_ID = "semdelion_notification_channel"

const val TOPIC="/topics/semdelionTopics"

class PushNotificationFirebaseMessagingService: FirebaseMessagingService() {

    companion object {
        var sharedPref: SharedPreferences? = null

        var token: String?
        get() {
            return sharedPref?.getString("pushToken", "")
        }
        set(value) {
            sharedPref?.edit()?.putString("pushToken", value)?.apply()
        }
    }

    override fun onCreate() {
        super.onCreate()
        sharedPref = getSharedPreferences("SharedPrefToken", MODE_PRIVATE)
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
    }

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        Log.d("PushNotificationFirebaseMessagingService", "Semdelion token: $newToken")
        token = newToken
    }


    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        Log.d("PushNotificationFirebaseMessagingService", "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(
                "PushNotificationFirebaseMessagingService",
                "Message data payload: ${remoteMessage.data}"
            )
        }

        val intent = Intent(this, RoutingActivity::class.java)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationID = Random.nextInt()

        createNotificationChannel(notificationManager)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent, FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(remoteMessage.data["title"])
            .setContentText(remoteMessage.data["message"])
            .setSmallIcon(R.drawable.ic_add_favorite)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(notificationID, notification)


        /*// TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("PushNotificationFirebaseMessagingService", "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d("PushNotificationFirebaseMessagingService", "Message data payload: ${remoteMessage.data}")

           /* if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                scheduleJob()
            } else {
                // Handle message within 10 seconds
                handleNow()
            }*/
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d("PushNotificationFirebaseMessagingService", "Message Notification Body: ${it.body}")
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.*/
    }


    private fun createNotificationChannel(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "SemdelionChannel", NotificationManager.IMPORTANCE_HIGH).apply {
                enableLights(true)
                lightColor = Color.GREEN
            }

            notificationManager.createNotificationChannel(serviceChannel)
        }
    }
}