package com.semdelion.presentaion

import com.semdelion.data.repositories.MessageRepositoryImpl
import com.semdelion.data.storages.SharedPrefMessageStorage
import com.semdelion.domain.core.coroutines.IoDispatcher
import com.semdelion.domain.core.coroutines.WorkerDispatcher
import com.semdelion.presentaion.core.SingletonScopeDependencies
import kotlinx.coroutines.Dispatchers

object Initializer {

    fun initDependencies() = SingletonScopeDependencies.init { applicationContext ->
        val ioDispatcher = IoDispatcher(Dispatchers.IO)
        val ioWorkerDispatcher = WorkerDispatcher(Dispatchers.Default)

        return@init listOf(
            ioWorkerDispatcher,
            MessageRepositoryImpl(SharedPrefMessageStorage(applicationContext), ioDispatcher)
        )
    }
}