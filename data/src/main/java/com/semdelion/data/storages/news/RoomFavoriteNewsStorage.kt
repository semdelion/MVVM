package com.semdelion.data.storages.news

import com.semdelion.data.storages.news.entities.FavoriteNewsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class RoomFavoriteNewsStorage(private val favoriteNewsDao: FavoriteNewsDao): IFavoriteNewsStorage {
    override fun getNews(): Flow<List<FavoriteNewsEntity>> {
        return favoriteNewsDao.getFavoriteNews()
    }

    override suspend fun addNews(news: FavoriteNewsEntity): Boolean {
        return withContext(Dispatchers.IO) {
            val result = favoriteNewsDao.insert(news)
            return@withContext result != -1L
        }
    }

    override suspend fun deleteNews(newsId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            val result = favoriteNewsDao.deleteById(newsId)
            return@withContext result > 0
        }
    }
}