package com.semdelion.domain.core

import com.semdelion.domain.repositories.IRepository

typealias TaskBody<T> = () -> T

interface TasksFactory : IRepository {
    fun <T> async(body: TaskBody<T>): Task<T>
}