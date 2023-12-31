package com.semdelion.presentation

import com.semdelion.data.repositories.AccountsRepositoryImpl
import com.semdelion.data.repositories.FavoriteNewsRepositoryImpl
import com.semdelion.data.repositories.MessageRepositoryImpl
import com.semdelion.data.repositories.NewsRepositoryImpl
import com.semdelion.data.apis.ApisConfig
import com.semdelion.data.repositories.NotificationRepositoryImpl
import com.semdelion.data.storages.message.SharedPrefMessageStorage
import com.semdelion.data.storages.Storages
import com.semdelion.data.storages.account.SharedPreferencesAppSettings
import com.semdelion.domain.core.coroutines.IoDispatcher
import com.semdelion.domain.core.coroutines.WorkerDispatcher
import com.semdelion.domain.usecases.news.GetNewsUseCase
import com.semdelion.domain.usecases.news.DeleteNewsUseCase
import com.semdelion.domain.usecases.news.GetFavoriteNewsUseCase
import com.semdelion.domain.usecases.news.SaveNewsUseCase
import com.semdelion.presentation.core.SingletonScopeDependencies
import kotlinx.coroutines.Dispatchers

object Initializer {

    fun initDependencies() = SingletonScopeDependencies.init { applicationContext ->

        Storages.init(applicationContext)
        ApisConfig.init(applicationContext)

        val ioDispatcher = IoDispatcher(Dispatchers.IO)
        val ioWorkerDispatcher = WorkerDispatcher(Dispatchers.Default)
        val appSettings = SharedPreferencesAppSettings(applicationContext)
        val accountsRepository = AccountsRepositoryImpl(accountsStorage = Storages.accountsStorage, appSettings = appSettings, ioDispatcher = ioDispatcher)
        val notificationRepository = NotificationRepositoryImpl(ApisConfig.httpClient, ioDispatcher)
        val getNewsUseCase = GetNewsUseCase(newsRepository = NewsRepositoryImpl(ApisConfig.httpClient, ioDispatcher))
        val getFavoriteNewsUseCase = GetFavoriteNewsUseCase(favoriteNews = FavoriteNewsRepositoryImpl(Storages.favoriteNewsStorage, ioDispatcher))
        val saveNewsUseCase = SaveNewsUseCase(favoriteNews = FavoriteNewsRepositoryImpl(Storages.favoriteNewsStorage, ioDispatcher))
        val deleteNewsUseCase = DeleteNewsUseCase(favoriteNews = FavoriteNewsRepositoryImpl(Storages.favoriteNewsStorage, ioDispatcher))

        return@init listOf(
            ioWorkerDispatcher,
            MessageRepositoryImpl(SharedPrefMessageStorage(applicationContext), ioDispatcher),
            accountsRepository,
            getNewsUseCase,
            saveNewsUseCase,
            getFavoriteNewsUseCase,
            deleteNewsUseCase,
            notificationRepository
        )
    }
}