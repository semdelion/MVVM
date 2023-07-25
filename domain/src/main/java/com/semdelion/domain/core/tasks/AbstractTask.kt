package com.semdelion.domain.core.tasks

import com.semdelion.domain.core.tasks.dispatchers.Dispatcher
import com.semdelion.domain.core.utils.Await
import com.semdelion.domain.models.ErrorResult
import com.semdelion.domain.models.FinalResult
import com.semdelion.domain.models.SuccessResult
import java.lang.Exception

abstract class AbstractTask<T> : Task<T> {

    private var finalResult by Await<FinalResult<T>>()

    final override fun await(): T {
        val wrapperListener: TaskListener<T> = {
            finalResult = it
        }
        doEnqueue(wrapperListener)
        try {
            when (val result = finalResult) {
                is ErrorResult -> throw result.exception
                is SuccessResult -> return result.data
            }
        } catch (e: Exception) {
            if (e is InterruptedException) {
                cancel()
                throw CancelledException(e)
            } else {
                throw e
            }
        }
    }


    override fun enqueue(dispatcher: Dispatcher, listener: TaskListener<T>) {
        val wrapperListener: TaskListener<T> = {
            finalResult = it
            dispatcher.dispatch {
                listener(finalResult)
            }

        }
        doEnqueue(wrapperListener)
    }

    fun executeBody(taskBody: TaskBody<T>, listener: TaskListener<T>) {
        try {
            val data = taskBody()
            listener(SuccessResult(data))
        } catch (e: Exception) {
            listener(ErrorResult(e))
        }
    }

    override fun cancel() {
        finalResult = ErrorResult(CancelledException())
        doCancel()
    }


    abstract fun doEnqueue(listener: TaskListener<T>)

    abstract fun doCancel()
}