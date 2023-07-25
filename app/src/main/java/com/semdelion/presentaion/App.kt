package com.semdelion.presentaion

import android.app.Application
import com.semdelion.data.repositories.MessageRepositoryImpl
import com.semdelion.data.storages.SharedPrefMessageStorage
import com.semdelion.domain.core.tasks.ThreadUtils
import com.semdelion.presentaion.core.BaseApplication
import com.semdelion.presentaion.core.tasks.ThreadTasksFactory
import com.semdelion.presentaion.core.tasks.dispatchers.MainThreadDispatcher

class App : Application(), BaseApplication {

    override lateinit var singletonScopeDependencies: List<Any>

    private val threadUtils = ThreadUtils.Default()
    private val dispatcher = MainThreadDispatcher()
    private val tasksFactory = ThreadTasksFactory()

    override fun onCreate() {
        super.onCreate()
        singletonScopeDependencies = listOf(
            dispatcher,
            tasksFactory,
            MessageRepositoryImpl(SharedPrefMessageStorage(applicationContext), tasksFactory, threadUtils))
    }
}