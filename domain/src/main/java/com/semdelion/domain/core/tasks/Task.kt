package com.semdelion.domain.core.tasks

import com.semdelion.domain.core.tasks.dispatchers.Dispatcher
import com.semdelion.domain.models.FinalResult

typealias TaskListener<T> = (FinalResult<T>) -> Unit

class CancelledException(
    originException: java.lang.Exception? = null
) : Exception(originException)

interface Task<T> {

    fun await(): T

    fun enqueue(dispatcher: Dispatcher, listener: TaskListener<T>)

    fun cancel()
}