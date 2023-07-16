package com.semdelion.mvvm

import android.app.Application

class App: Application() {

    val models = listOf<Any>()

    override fun onCreate() {
        super.onCreate()
    }
}