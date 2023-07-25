package com.semdelion.presentaion.core.tasks

import com.semdelion.domain.core.tasks.AbstractTask
import com.semdelion.domain.core.tasks.SynchronizedTask
import com.semdelion.domain.core.tasks.Task
import com.semdelion.domain.core.tasks.TaskBody
import com.semdelion.domain.core.tasks.TaskListener
import com.semdelion.domain.core.tasks.TasksFactory
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

class ExecutorServiceTasksFactory(
    private val executorService: ExecutorService
) : TasksFactory {

    override fun <T> async(body: TaskBody<T>): Task<T> {
        return SynchronizedTask(ExecutorServiceTask(body))
    }

    private inner class ExecutorServiceTask<T>(
        private val body: TaskBody<T>
    ): AbstractTask<T>() {

        private var future: Future<*>? = null

        override fun doEnqueue(listener: TaskListener<T>) {
            future = executorService.submit {
                executeBody(body, listener)
            }
        }

        override fun doCancel() {
            future?.cancel(true)
        }
    }
}