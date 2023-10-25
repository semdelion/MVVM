package com.semdelion.presentation.di

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
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {

    @Provides
    @Singleton
    fun bindsNewsUseCase(newsRepository: INewsRepository): GetNewsUseCase {
        return GetNewsUseCase(newsRepository)
    }

    @Provides
    @Singleton
    fun bindsFavoriteNewsUseCase(favoriteNews: IFavoriteNewsRepository): GetFavoriteNewsUseCase {
        return GetFavoriteNewsUseCase(favoriteNews)
    }

    @Provides
    @Singleton
    fun bindsSaveNewsUseCase(favoriteNews: IFavoriteNewsRepository): SaveNewsUseCase {
        return SaveNewsUseCase(favoriteNews)
    }

    @Provides
    @Singleton
    fun bindsDeleteNewsUseCase(favoriteNews: IFavoriteNewsRepository): DeleteNewsUseCase {
        return DeleteNewsUseCase(favoriteNews)
    }
}