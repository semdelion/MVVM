package com.semdelion.presentaion.core.tasks

import android.os.Handler
import android.os.Looper
import com.semdelion.domain.core.Task
import com.semdelion.domain.core.TaskBody
import com.semdelion.domain.core.TaskListener
import com.semdelion.domain.core.TasksFactory
import com.semdelion.domain.models.ErrorResult
import com.semdelion.domain.models.FinalResult
import com.semdelion.domain.models.SuccessResult
import kotlinx.coroutines.NonCancellable.start
import kotlinx.coroutines.withContext

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

        override fun enqueue(listener: TaskListener<T>) {
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