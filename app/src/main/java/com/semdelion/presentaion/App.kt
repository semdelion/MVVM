package com.semdelion.presentaion

import android.app.Application
import com.semdelion.data.repositories.MessageRepositoryImpl
import com.semdelion.data.storages.SharedPrefMessageStorage
import com.semdelion.domain.repositories.IMessageRepository
import com.semdelion.domain.repositories.IRepository
import com.semdelion.presentaion.core.BaseApplication
import com.semdelion.presentaion.core.tasks.SimpleTaskFactory

class App : Application(), BaseApplication {

    override lateinit var repositories: List<IRepository>

    override fun onCreate() {
        super.onCreate()
        repositories = listOf<IRepository>(
            MessageRepositoryImpl(SharedPrefMessageStorage(applicationContext), SimpleTaskFactory()),
            SimpleTaskFactory())
    }
}