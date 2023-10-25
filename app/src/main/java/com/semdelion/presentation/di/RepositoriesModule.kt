package com.semdelion.presentation.di

import com.semdelion.data.repositories.AccountsRepositoryImpl
import com.semdelion.data.repositories.FavoriteNewsRepositoryImpl
import com.semdelion.data.repositories.MessageRepositoryImpl
import com.semdelion.data.repositories.NewsRepositoryImpl
import com.semdelion.data.storages.AppDatabase
import com.semdelion.data.storages.account.AccountRoomStorage
import com.semdelion.data.storages.account.IAccountsStorage
import com.semdelion.data.storages.message.IMessageStorage
import com.semdelion.data.storages.message.SharedPrefMessageStorage
import com.semdelion.data.storages.news.FavoriteNewsRoomStorage
import com.semdelion.data.storages.news.IFavoriteNewsStorage
import com.semdelion.domain.repositories.accounts.IAccountsRepository
import com.semdelion.domain.repositories.message.IMessageRepository
import com.semdelion.domain.repositories.news.IFavoriteNewsRepository
import com.semdelion.domain.repositories.news.INewsRepository
import com.semdelion.domain.usecases.news.DeleteNewsUseCase
import com.semdelion.domain.usecases.news.GetFavoriteNewsUseCase
import com.semdelion.domain.usecases.news.GetNewsUseCase
import com.semdelion.domain.usecases.news.SaveNewsUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindsAccountsRepository(accountsRepositoryImpl: AccountsRepositoryImpl): IAccountsRepository

    @Binds
    abstract fun bindsMessageRepository(messageRepositoryImpl: MessageRepositoryImpl): IMessageRepository

    @Binds
    abstract fun bindsNewsRepository(newsRepository: NewsRepositoryImpl ): INewsRepository

    @Binds
    abstract fun bindsFavoriteNewsRepository(favoriteNewsRepository: FavoriteNewsRepositoryImpl): IFavoriteNewsRepository
}