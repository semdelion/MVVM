package com.semdelion.presentation.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.semdelion.data.utils.FileUpLoader
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executors

@AndroidEntryPoint
class BackgroundServices: Service() {

    private val fileUpLoader = FileUpLoader()

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        Log.i("Semdelion", "BackgroundServices onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("Semdelion", "BackgroundServices onStartCommand")
        Executors.newSingleThreadExecutor().execute {
            fileUpLoader.upload(
                object : FileUpLoader.ProgressCallBack {
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
        Log.i("Semdelion", "BackgroundServices onDestroy")
    }
}