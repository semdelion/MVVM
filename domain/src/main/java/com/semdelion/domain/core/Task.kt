package com.semdelion.domain.core

import com.semdelion.domain.models.FinalResult

typealias TaskListener<T> = (FinalResult<T>) -> Unit

interface Task<T> {

    fun await(): T

    fun enqueue(listener: TaskListener<T>)

    fun cancel()
}