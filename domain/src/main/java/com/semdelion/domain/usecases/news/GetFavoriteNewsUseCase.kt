package com.semdelion.domain.usecases.news

import com.semdelion.domain.repositories.news.models.NewsModel
import com.semdelion.domain.repositories.news.IFavoriteNewsRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteNewsUseCase(private val favoriteNews: IFavoriteNewsRepository) {
    suspend fun getFavoriteNews(): Flow<List<NewsModel>> {
        return favoriteNews.getFavoriteNews()
    }
}