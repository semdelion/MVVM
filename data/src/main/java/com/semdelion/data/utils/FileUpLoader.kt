package com.semdelion.data.utils

import android.util.Log
import java.lang.Exception

class FileUpLoader() {
    interface ProgressCallBack {
        fun onProgress(progress: Int)
    }

    @Volatile
    var isInterrupted = false

    fun  upload(notify: ProgressCallBack)  {
        Log.i("Semdelion", "File upload started")

        try {
            for (i in 1..100 ) {
                if(isInterrupted)
                    break
                notify.onProgress(i)
                Thread.sleep(250)
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