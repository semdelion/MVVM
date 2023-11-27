package com.semdelion.presentation.services.works

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.semdelion.data.utils.FileUpLoader

//TODO need research //https://developer.android.com/codelabs/android-workmanager#3
//Di https://medium.com/@jeremy.leyvraz/the-art-of-integrating-hilt-dependency-injection-with-workers-for-harmonious-android-development-28bdc21be047
class UploadWorker(private val context: Context, private val workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

    private val fileUpLoader = FileUpLoader()

    override suspend fun doWork(): Result {
        Log.i("Semdelion", "Upload doWork")

        val filename = workerParams.inputData.getString(KEY_CONTENT_URI)
        if(filename == null ) {
            Log.e("Semdelion", "error filename is null!")
            return Result.failure()
        }

        fileUpLoader.upload(
            object : FileUpLoader.ProgressCallBack {
                override fun onProgress(progress: Int) {
                    if(isStopped) {
                        Log.i("Semdelion", "Stopped")
                        fileUpLoader.cancel()
                        Result.retry()
                    } else {
                        Log.i("Semdelion", "${Thread.currentThread().name} Progress $progress%")
                    }

                }
            }
        )

        return Result.success(
            workDataOf(
                KEY_RESULT_PATH to "file_path"
            )
        )
    }

    companion object {
        const val KEY_CONTENT_URI = "KEY_CONTENT_FILE_NAME"
        const val KEY_RESULT_PATH = "KEY_RESULT_PATH"
    }
}