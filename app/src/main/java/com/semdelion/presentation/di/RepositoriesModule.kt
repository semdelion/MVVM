package com.semdelion.presentation.di

import com.semdelion.data.repositories.AccountsRepositoryImpl
import com.semdelion.data.repositories.FavoriteNewsRepositoryImpl
import com.semdelion.data.repositories.MessageRepositoryImpl
import com.semdelion.data.repositories.NewsRepositoryImpl
import com.semdelion.domain.repositories.accounts.IAccountsRepository
import com.semdelion.domain.repositories.message.IMessageRepository
import com.semdelion.domain.repositories.news.IFavoriteNewsRepository
import com.semdelion.domain.repositories.news.INewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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