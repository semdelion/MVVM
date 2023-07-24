package com.semdelion.domain.core.tasks.dispatchers

interface Dispatcher {

    fun dispatch(block: () -> Unit)
}