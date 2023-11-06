package com.semdelion.presentation.ui.tabs.dashboard.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.concurrent.Executors


interface ProgressCallBack {
     fun onProgress(progress: Int)
}

class FileUpLoader() {
    fun upload(notify: ProgressCallBack) {
        for (i in 1..100) {
            notify.onProgress(i)
            Thread.sleep(1000)
        }
    }
}

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
        Log.i("Semdelion", "BackgroundServices onDestroy")
    }
}