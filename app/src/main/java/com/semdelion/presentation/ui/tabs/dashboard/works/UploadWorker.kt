package com.semdelion.presentation.ui.tabs.dashboard.works

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.semdelion.presentation.ui.tabs.dashboard.services.FileUpLoader
import com.semdelion.presentation.ui.tabs.dashboard.services.ProgressCallBack

class UploadWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private val fileUpLoader = FileUpLoader()

    override fun doWork(): Result {
        Log.i("Semdelion", "doWork")
        fileUpLoader.upload(
            object : ProgressCallBack {
                override fun onProgress(progress: Int) {
                    if(isStopped) {
                        Log.i("Semdelion", "Stopped")
                        fileUpLoader.cancel()
                    } else {
                        Log.i("Semdelion", "${Thread.currentThread().name} Progress $progress%")
                    }

                }
            }
        )

        return Result.success()
    }
}