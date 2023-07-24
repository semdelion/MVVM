package com.semdelion.presentaion.core.tasks.dispatchers

import android.os.Handler
import android.os.Looper
import com.semdelion.domain.core.tasks.dispatchers.Dispatcher

class MainThreadDispatcher : Dispatcher {

    private val handler = Handler(Looper.getMainLooper())

    override fun dispatch(block: () -> Unit) {
        if (Looper.getMainLooper().thread.id == Thread.currentThread().id) {
            block()
        } else {
            handler.post(block)
        }
    }
}