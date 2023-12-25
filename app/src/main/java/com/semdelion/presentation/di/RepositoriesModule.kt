package com.semdelion.presentation.di

import com.semdelion.data.repositories.AccountsRepositoryImpl
import com.semdelion.data.repositories.FavoriteNewsRepositoryImpl
import com.semdelion.data.repositories.MessageRepositoryImpl
import com.semdelion.data.repositories.NewsRepositoryImpl
import com.semdelion.data.repositories.NotificationRepositoryImpl
import com.semdelion.domain.repositories.accounts.IAccountsRepository
import com.semdelion.domain.repositories.message.IMessageRepository
import com.semdelion.domain.repositories.news.IFavoriteNewsRepository
import com.semdelion.domain.repositories.news.INewsRepository
import com.semdelion.domain.repositories.notification.INotificationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoriesModule {

    @Binds
    fun bindsAccountsRepository(accountsRepositoryImpl: AccountsRepositoryImpl): IAccountsRepository

    @Binds
    fun bindsMessageRepository(messageRepositoryImpl: MessageRepositoryImpl): IMessageRepository

    @Binds
    fun bindsNewsRepository(newsRepository: NewsRepositoryImpl ): INewsRepository

    @Binds
    fun bindsFavoriteNewsRepository(favoriteNewsRepository: FavoriteNewsRepositoryImpl): IFavoriteNewsRepository

    @Binds
    fun bindsNotificationRepository(notificationRepository: NotificationRepositoryImpl): INotificationRepository
}