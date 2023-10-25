package com.semdelion.data.repositories

import com.semdelion.data.core.storages.BaseRoomStorages
import com.semdelion.data.storages.news.IFavoriteNewsStorage
import com.semdelion.data.storages.news.entities.FavoriteNewsEntity
import com.semdelion.domain.core.coroutines.IoDispatcher
import com.semdelion.domain.repositories.news.models.NewsModel
import com.semdelion.domain.repositories.news.IFavoriteNewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteNewsRepositoryImpl @Inject constructor(
    private val favoriteNewsStorage: IFavoriteNewsStorage,
    private val ioDispatcher: IoDispatcher
    ) : BaseRoomStorages(), IFavoriteNewsRepository {
    override suspend fun addFavoriteNews(newsModel: NewsModel): Boolean = wrapSQLiteException(ioDispatcher.value) {

        val newsEntity = FavoriteNewsEntity(
            id = newsModel.hashCode(),
            title = newsModel.title,
            link = newsModel.link,
            creator = newsModel.creator,
            content = newsModel.content,
            pubDate = newsModel.pubDate,
            imageURL = newsModel.imageURL,
        )

        return@wrapSQLiteException favoriteNewsStorage.addNews(newsEntity)
    }

    override suspend fun getFavoriteNews(): Flow<List<NewsModel>> = wrapSQLiteException(ioDispatcher.value) {
        val result = favoriteNewsStorage.getNews()

        return@wrapSQLiteException result.map {
            val list = mutableListOf<NewsModel>()
            if (it.isNotEmpty()) {
                it.forEach {item ->  list.add(item.toFavoriteNewsModel())}
            }
            list
        }
    }

    override suspend fun deleteFavoriteNews(key: NewsModel): Boolean = wrapSQLiteException(ioDispatcher.value) {
        return@wrapSQLiteException favoriteNewsStorage.deleteNews(key.hashCode())
    }
}