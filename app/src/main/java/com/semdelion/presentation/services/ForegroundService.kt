package com.semdelion.presentation.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.semdelion.data.utils.FileUpLoader
import com.semdelion.presentation.R
import com.semdelion.presentation.RoutingActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executors

private const val NOTIFICATION_ID = 1
private const val CHANNEL_ID = "ForegroundService_channel"

@AndroidEntryPoint
class ForegroundService: Service() {

    private val fileUpLoader = FileUpLoader()

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel(getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
        Log.i("Semdelion", "ForegroundService onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val pendingIntent: PendingIntent =
            Intent(this, RoutingActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(
                    this, 0, notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            }

        startForeground(
            NOTIFICATION_ID,
            createNotification(this,
                "Foreground service",
                "Starting...",
                pendingIntent
            )
        )

        Executors.newSingleThreadExecutor().execute {
            fileUpLoader.upload(
                object : FileUpLoader.ProgressCallBack {
                    override fun onProgress(progress: Int) {
                        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                        notificationManager.notify(
                            NOTIFICATION_ID,
                            createNotification(this@ForegroundService,
                                "Foreground service - started",
                                "Progress $progress%",
                                pendingIntent)
                        )
                    }
                }
            )
            stopSelf()
        }
        return START_NOT_STICKY
    }

    private fun createNotification(context: Context, title: String, contentText: String, pendingIntent: PendingIntent): Notification {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(contentText)
            .setSmallIcon(R.drawable.ic_add_favorite)
            .setContentIntent(pendingIntent)
            .setSilent(true)
            .build()
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_MIN
            ).apply {
                enableVibration(false)
            }

            notificationManager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fileUpLoader.cancel()
        Log.i("Semdelion", "ForegroundService onDestroy")
    }
}