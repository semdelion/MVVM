package com.semdelion.domain.repositories.news

import com.semdelion.domain.repositories.news.models.NewsModel
import kotlinx.coroutines.flow.Flow

interface IFavoriteNewsRepository {
    suspend fun addFavoriteNews(newsModel: NewsModel): Boolean

    fun getFavoriteNews(): Flow<List<NewsModel>>

    suspend fun deleteFavoriteNews(key: NewsModel): Boolean
}