package com.semdelion.presentation

import com.semdelion.data.repositories.FavoriteNewsRepositoryImpl
import com.semdelion.data.repositories.InMemoryAccountsRepository
import com.semdelion.data.repositories.MessageRepositoryImpl
import com.semdelion.data.repositories.NewsRepositoryImpl
import com.semdelion.data.storages.SharedPrefMessageStorage
import com.semdelion.data.storages.Storages
import com.semdelion.domain.core.coroutines.IoDispatcher
import com.semdelion.domain.core.coroutines.WorkerDispatcher
import com.semdelion.domain.usecases.GetNewsUseCase
import com.semdelion.domain.usecases.news.DeleteNewsUseCase
import com.semdelion.domain.usecases.news.GetFavoriteNewsUseCase
import com.semdelion.domain.usecases.news.SaveNewsUseCase
import com.semdelion.presentation.core.SingletonScopeDependencies
import kotlinx.coroutines.Dispatchers

object Initializer {

    fun initDependencies() = SingletonScopeDependencies.init { applicationContext ->

        Storages.init(applicationContext)

        val ioDispatcher = IoDispatcher(Dispatchers.IO)
        val ioWorkerDispatcher = WorkerDispatcher(Dispatchers.Default)
        val accountsRepository = InMemoryAccountsRepository()
        val getNewsUseCase = GetNewsUseCase(NewsRepositoryImpl())
        val getFavoriteNewsUseCase = GetFavoriteNewsUseCase(FavoriteNewsRepositoryImpl(Storages.favoriteNewsStorage))
        val saveNewsUseCase = SaveNewsUseCase(favoriteNews = FavoriteNewsRepositoryImpl(Storages.favoriteNewsStorage))
        val deleteNewsUseCase = DeleteNewsUseCase(favoriteNews = FavoriteNewsRepositoryImpl(Storages.favoriteNewsStorage))

        return@init listOf(
            ioWorkerDispatcher,
            MessageRepositoryImpl(SharedPrefMessageStorage(applicationContext), ioDispatcher),
            accountsRepository,
            getNewsUseCase,
            saveNewsUseCase,
            getFavoriteNewsUseCase,
            deleteNewsUseCase
        )
    }
}