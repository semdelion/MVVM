package com.semdelion.presentation.ui.tabs.dashboard.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.lang.Exception
import java.util.concurrent.Executors


interface ProgressCallBack {
     fun onProgress(progress: Int)
}

class FileUpLoader() {

    @Volatile
    var isInterrupted = false

    fun  upload(notify: ProgressCallBack)  {
        Log.i("Semdelion", "File upload started")

        try {
            for (i in 1..100 ) {
                if(isInterrupted)
                    break
                notify.onProgress(i)
                Thread.sleep(100)
            }
        }
        catch (ex: InterruptedException) {
            Log.e("InterruptedException", ex.message ?: "InterruptedException")
        }
        catch (ex: Exception) {
            Log.e("Exception", ex.message ?: "Exception")
        }
    }

    fun cancel() {
        Log.i("Semdelion", "File upload canceled")
        isInterrupted = true
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