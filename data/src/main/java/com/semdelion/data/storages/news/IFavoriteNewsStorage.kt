package com.semdelion.data.storages.news

import com.semdelion.data.storages.news.entities.FavoriteNewsEntity
import kotlinx.coroutines.flow.Flow

interface IFavoriteNewsStorage {
    fun getNews(): Flow<List<FavoriteNewsEntity>>
    suspend fun addNews(news: FavoriteNewsEntity) : Boolean
    suspend fun deleteNews(newsId: Int) : Boolean
}