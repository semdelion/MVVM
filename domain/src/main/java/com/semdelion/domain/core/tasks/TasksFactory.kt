package com.semdelion.domain.core.tasks

typealias TaskBody<T> = () -> T

interface TasksFactory {
    fun <T> async(body: TaskBody<T>): Task<T>
}