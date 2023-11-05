package com.semdelion.presentation.di

import com.semdelion.domain.repositories.news.IFavoriteNewsRepository
import com.semdelion.domain.repositories.news.INewsRepository
import com.semdelion.domain.usecases.news.DeleteNewsUseCase
import com.semdelion.domain.usecases.news.GetFavoriteNewsUseCase
import com.semdelion.domain.usecases.news.GetNewsUseCase
import com.semdelion.domain.usecases.news.SaveNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCasesModule {

    @Provides
    fun providesNewsUseCase(newsRepository: INewsRepository): GetNewsUseCase {
        return GetNewsUseCase(newsRepository)
    }

    @Provides
    fun providesFavoriteNewsUseCase(favoriteNews: IFavoriteNewsRepository): GetFavoriteNewsUseCase {
        return GetFavoriteNewsUseCase(favoriteNews)
    }

    @Provides
    fun providesSaveNewsUseCase(favoriteNews: IFavoriteNewsRepository): SaveNewsUseCase {
        return SaveNewsUseCase(favoriteNews)
    }

    @Provides
    fun providesDeleteNewsUseCase(favoriteNews: IFavoriteNewsRepository): DeleteNewsUseCase {
        return DeleteNewsUseCase(favoriteNews)
    }
}