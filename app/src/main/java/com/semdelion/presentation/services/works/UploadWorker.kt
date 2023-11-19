package com.semdelion.presentation.services.works

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.semdelion.data.utils.FileUpLoader

class UploadWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private val fileUpLoader = FileUpLoader()

    override fun doWork(): Result {
        Log.i("Semdelion", "Upload doWork")
        fileUpLoader.upload(
            object : FileUpLoader.ProgressCallBack {
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