package com.semdelion.domain.core.tasks

import com.semdelion.domain.core.tasks.dispatchers.Dispatcher
import com.semdelion.domain.core.tasks.dispatchers.ImmediateDispatcher
import com.semdelion.domain.models.ErrorResult
import com.semdelion.domain.models.FinalResult
import com.semdelion.domain.models.SuccessResult
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

typealias TaskListener<T> = (FinalResult<T>) -> Unit

class CancelledException(
    originException: java.lang.Exception? = null
) : Exception(originException)

interface Task<T> {

    fun await(): T

    fun enqueue(dispatcher: Dispatcher, listener: TaskListener<T>)

    fun cancel()

    suspend fun suspend(): T = suspendCancellableCoroutine {cancellableContinuation ->
        enqueue(ImmediateDispatcher()) {
            cancellableContinuation.invokeOnCancellation { cancel() }
            when(it) {
                is SuccessResult -> cancellableContinuation.resume(it.data)
                is ErrorResult -> cancellableContinuation.resumeWithException(it.exception)
            }
        }
    }
}