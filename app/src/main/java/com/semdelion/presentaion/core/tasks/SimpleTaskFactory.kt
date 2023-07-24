package com.semdelion.presentaion.core.tasks

import android.os.Handler
import android.os.Looper
import com.semdelion.domain.core.tasks.Task
import com.semdelion.domain.core.tasks.TaskBody
import com.semdelion.domain.core.tasks.TaskListener
import com.semdelion.domain.core.tasks.TasksFactory
import com.semdelion.domain.core.tasks.dispatchers.Dispatcher
import com.semdelion.domain.models.ErrorResult
import com.semdelion.domain.models.FinalResult
import com.semdelion.domain.models.SuccessResult

private val handler = Handler(Looper.getMainLooper())

class SimpleTaskFactory : TasksFactory {

    override fun <T> async(body: TaskBody<T>): Task<T> {
        return SimpleTask(body)
    }

    class SimpleTask<T>(
        private val body: TaskBody<T>
    ) : Task<T> {

        var thread: Thread? = null
        var cancelled = false

        override fun await(): T {
            return body()
        }

        override fun cancel() {
            cancelled = true
            thread?.interrupt()
            thread = null
        }

        override fun enqueue(dispatcher: Dispatcher, listener: TaskListener<T>) {
            thread = Thread {
                try {
                    val data = body()
                    publishResult(listener, SuccessResult(data))
                } catch (e: Exception) {
                    publishResult(listener, ErrorResult(e))
                }
            }
            thread?.start()
        }

        private fun publishResult(listener: TaskListener<T>, result: FinalResult<T>) {
            handler.post {
                if (cancelled) return@post
                listener.invoke(result)
            }
        }

    }
}