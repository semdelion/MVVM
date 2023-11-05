package com.semdelion.presentation.di

import android.content.Context
import androidx.room.Room
import com.semdelion.data.storages.AppDatabase
import com.semdelion.data.storages.account.AccountRoomStorage
import com.semdelion.data.storages.account.AppSettings
import com.semdelion.data.storages.account.IAccountsStorage
import com.semdelion.data.storages.account.SharedPrefAppSettings
import com.semdelion.data.storages.message.IMessageStorage
import com.semdelion.data.storages.message.SharedPrefMessageStorage
import com.semdelion.data.storages.news.FavoriteNewsRoomStorage
import com.semdelion.data.storages.news.IFavoriteNewsStorage
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StoragesModule {

    @Provides
    @Singleton
    fun providerAppDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
         return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "database.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoriteNewsDao(appDatabase: AppDatabase) = appDatabase.getFavoriteNewsDao()

    @Provides
    @Singleton
    fun provideAccountsDao(appDatabase: AppDatabase) = appDatabase.getAccountsDao()

    @Provides
    @Singleton
    fun provideMessageStorage(@ApplicationContext applicationContext: Context): IMessageStorage {
        return SharedPrefMessageStorage(applicationContext)
    }

    @Provides
    @Singleton
    fun providesAppSettings(@ApplicationContext applicationContext: Context): AppSettings {
        return SharedPrefAppSettings(applicationContext)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class BindsStoragesModule {
        @Binds
        @Singleton
        abstract fun bindFavoriteNewsStorage(favoriteNewsRoomStorage: FavoriteNewsRoomStorage): IFavoriteNewsStorage

        @Binds
        @Singleton
        abstract fun bindAccountsStorage(accountRoomStorage: AccountRoomStorage): IAccountsStorage
    }
}

