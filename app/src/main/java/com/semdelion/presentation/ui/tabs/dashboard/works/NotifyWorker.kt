package com.semdelion.presentation.ui.tabs.dashboard.works

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotifyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {


    override fun doWork(): Result {
        Log.i("Semdelion", "Notify doWork")
        //TODO send notification message with sound - work is done (WoW3)

        return Result.success()
    }
}