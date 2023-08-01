package com.semdelion.presentaion

import android.app.Application
import com.semdelion.data.repositories.MessageRepositoryImpl
import com.semdelion.data.storages.SharedPrefMessageStorage
import com.semdelion.presentaion.core.BaseApplication
import com.semdelion.presentaion.core.tasks.dispatchers.MainThreadDispatcher

class App : Application(), BaseApplication {

    override lateinit var singletonScopeDependencies: List<Any>

    override fun onCreate() {
        super.onCreate()
        singletonScopeDependencies = listOf(
            MessageRepositoryImpl(SharedPrefMessageStorage(applicationContext)))
    }
}