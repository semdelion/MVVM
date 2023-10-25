package com.semdelion.presentation.di

import com.semdelion.domain.core.coroutines.IoDispatcher
import com.semdelion.domain.core.coroutines.WorkerDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {

    @Provides
    @Singleton
    fun provideIoDispatcher() : IoDispatcher {
        return IoDispatcher(Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun provideWorkerDispatcher() : WorkerDispatcher {
        return WorkerDispatcher(Dispatchers.Default)
    }
}