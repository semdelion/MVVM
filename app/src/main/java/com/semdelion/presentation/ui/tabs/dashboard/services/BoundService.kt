package com.semdelion.presentation.ui.tabs.dashboard.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.semdelion.data.utils.FileUpLoader
import java.util.concurrent.Executors

class BoundService: Service() {

    private var onProgress: ((Int) -> Unit)? = null

    private val fileUpLoader = FileUpLoader()

    inner class UploadBinder: Binder() {
        fun subscribeToProgress(onProgress: (Int) -> Unit) {
            this@BoundService.onProgress = onProgress
        }
    }

    override fun onBind(p0: Intent?): IBinder = UploadBinder()

    override fun onCreate() {
        super.onCreate()
        Log.i("Semdelion", "BoundService onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("Semdelion", "BoundService onStartCommand")

        val executorService = Executors.newSingleThreadExecutor()
        executorService?.execute {
            fileUpLoader.upload(
                object : FileUpLoader.ProgressCallBack {
                    override fun onProgress(progress: Int) {
                        Log.i("Semdelion", "${Thread.currentThread().name} Progress $progress%")
                        onProgress?.invoke(progress)
                    }
                }
            )
            stopSelf()
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        fileUpLoader.cancel()
        super.onDestroy()
        Log.i("Semdelion", "BoundService onDestroy")
    }
}
