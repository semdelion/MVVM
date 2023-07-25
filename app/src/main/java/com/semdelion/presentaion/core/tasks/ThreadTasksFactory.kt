package com.semdelion.presentaion.core.tasks

import com.semdelion.domain.core.tasks.AbstractTask
import com.semdelion.domain.core.tasks.SynchronizedTask
import com.semdelion.domain.core.tasks.Task
import com.semdelion.domain.core.tasks.TaskBody
import com.semdelion.domain.core.tasks.TaskListener
import com.semdelion.domain.core.tasks.TasksFactory

class ThreadTasksFactory : TasksFactory {

    override fun <T> async(body: TaskBody<T>): Task<T> {
        return SynchronizedTask(ThreadTask(body))
    }

    private class ThreadTask<T>(
        private val body: TaskBody<T>
    ) : AbstractTask<T>() {

        private var thread: Thread? = null
        override fun doEnqueue(listener: TaskListener<T>) {
            thread = Thread {
                executeBody(body, listener)
            }
            thread?.start()
        }

        override fun doCancel() {
            thread?.interrupt()
        }
    }
}