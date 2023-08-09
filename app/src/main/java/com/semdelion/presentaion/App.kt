package com.semdelion.presentaion

import android.app.Application
import com.semdelion.data.repositories.MessageRepositoryImpl
import com.semdelion.data.storages.SharedPrefMessageStorage
import com.semdelion.domain.core.coroutines.IoDispatcher
import com.semdelion.domain.core.coroutines.WorkerDispatcher
import com.semdelion.presentaion.core.BaseApplication
import com.semdelion.presentaion.core.utils.dispatchers.MainThreadDispatcher
import kotlinx.coroutines.Dispatchers

class App : Application(), BaseApplication {

    private val ioDispatcher = IoDispatcher(Dispatchers.IO)
    private val ioWorkerDispatcher = WorkerDispatcher(Dispatchers.Default)


    override lateinit var singletonScopeDependencies: List<Any>

    override fun onCreate() {
        super.onCreate()
        singletonScopeDependencies = listOf(
            MessageRepositoryImpl(SharedPrefMessageStorage(applicationContext), ioDispatcher))
    }
}