package com.semdelion.presentaion

import android.app.Application
import com.semdelion.domain.repositories.IRepository
import com.semdelion.presentaion.core.BaseApplication

class App : Application(), BaseApplication {
    override val repositories: List<IRepository> = listOf<IRepository>()

    override fun onCreate() {
        super.onCreate()
    }
}