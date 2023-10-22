package com.semdelion.presentation

import android.content.Context
import com.semdelion.data.BuildConfig
import com.semdelion.data.core.services.client.ApiClient
import com.semdelion.data.core.services.client.intercepters.LoggingInterceptor
import com.semdelion.data.core.services.client.intercepters.NetworkConnectionInterceptor
import com.semdelion.data.core.services.client.intercepters.NewsAuthInterceptor
import com.semdelion.data.repositories.AccountsRepositoryImpl
import com.semdelion.data.repositories.FavoriteNewsRepositoryImpl
import com.semdelion.data.repositories.MessageRepositoryImpl
import com.semdelion.data.repositories.NewsRepositoryImpl
import com.semdelion.data.storages.message.SharedPrefMessageStorage
import com.semdelion.data.storages.Storages
import com.semdelion.data.storages.account.AppSettings
import com.semdelion.data.storages.account.IAccountsStorage
import com.semdelion.data.storages.account.SharedPreferencesAppSettings
import com.semdelion.domain.core.coroutines.IoDispatcher
import com.semdelion.domain.core.coroutines.WorkerDispatcher
import com.semdelion.domain.usecases.news.GetNewsUseCase
import com.semdelion.domain.usecases.news.DeleteNewsUseCase
import com.semdelion.domain.usecases.news.GetFavoriteNewsUseCase
import com.semdelion.domain.usecases.news.SaveNewsUseCase
import com.semdelion.presentation.core.SingletonScopeDependencies
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

object Initializer {
    private fun initHttpClient(context: Context): ApiClient {
        val okHttpClient = OkHttpClient()
            .newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        okHttpClient.addInterceptor(LoggingInterceptor.defaultInterceptor())
        okHttpClient.addInterceptor(NewsAuthInterceptor())
        okHttpClient.addInterceptor(NetworkConnectionInterceptor(context))


        return ApiClient("https://newsdata.io/api/", okHttpClient)
    }

    fun initDependencies() = SingletonScopeDependencies.init { applicationContext ->

        Storages.init(applicationContext)

        val apiClient = initHttpClient(applicationContext)
        val ioDispatcher = IoDispatcher(Dispatchers.IO)
        val ioWorkerDispatcher = WorkerDispatcher(Dispatchers.Default)
        val appSettings = SharedPreferencesAppSettings(applicationContext)
        val accountsRepository = AccountsRepositoryImpl(accountsStorage = Storages.accountsStorage, appSettings = appSettings, ioDispatcher = ioDispatcher)
        val getNewsUseCase = GetNewsUseCase(newsRepository = NewsRepositoryImpl(apiClient, ioDispatcher))
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
            deleteNewsUseCase
        )
    }
}