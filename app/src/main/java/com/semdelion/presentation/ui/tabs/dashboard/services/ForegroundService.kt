package com.semdelion.presentation.ui.tabs.dashboard.services

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.concurrent.Executors

private const val NOTIFICATION_ID = 1

class ForegroundService: Service() {

    private val fileUpLoader = FileUpLoader()

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        Log.i("Semdelion", "ForegroundService onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

//TODO
       /* startForeground(
            NOTIFICATION_ID,

        )*/
        Log.i("Semdelion", "ForegroundService onStartCommand")
        Executors.newSingleThreadExecutor().execute {
            fileUpLoader.upload(
                object : ProgressCallBack {
                    override fun onProgress(progress: Int) {
                        Log.i("Semdelion", "${Thread.currentThread().name} Progress $progress%")
                    }
                }
            )
            stopSelf()
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Semdelion", "ForegroundService onDestroy")
    }
}