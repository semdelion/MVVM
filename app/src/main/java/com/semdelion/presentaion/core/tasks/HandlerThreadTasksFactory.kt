package com.semdelion.presentaion.core.tasks

import android.os.Handler
import android.os.HandlerThread
import com.semdelion.domain.core.tasks.AbstractTask
import com.semdelion.domain.core.tasks.SynchronizedTask
import com.semdelion.domain.core.tasks.Task
import com.semdelion.domain.core.tasks.TaskBody
import com.semdelion.domain.core.tasks.TaskListener
import com.semdelion.domain.core.tasks.TasksFactory
import java.util.concurrent.Future

class HandlerThreadTasksFactory: TasksFactory {
    private val thread = HandlerThread(javaClass.simpleName)

    init {
        thread.start()
    }

    private val handler = Handler(thread.looper)

    override fun <T> async(body: TaskBody<T>): Task<T> {
        return SynchronizedTask(HandlerThreadTask(body))
    }

    private inner class HandlerThreadTask<T>(
        private val body: TaskBody<T>
    ) : AbstractTask<T>() {

        private var thread: Thread? = null

        override fun doEnqueue(listener: TaskListener<T>) {
            val runnable = Runnable {
                thread = Thread {
                    executeBody(body, listener)
                }
                thread?.start()
            }
            handler.post(runnable)
        }

        override fun doCancel() {
            thread?.interrupt()
        }
    }
}