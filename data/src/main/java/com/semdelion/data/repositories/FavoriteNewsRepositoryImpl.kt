package com.semdelion.data.repositories

import com.semdelion.data.storages.news.IFavoriteNewsStorage
import com.semdelion.data.storages.news.entities.FavoriteNewsEntity
import com.semdelion.domain.repositories.news.models.NewsModel
import com.semdelion.domain.repositories.news.IFavoriteNewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteNewsRepositoryImpl(private val favoriteNewsStorage: IFavoriteNewsStorage) :
    IFavoriteNewsRepository {
    override suspend fun addFavoriteNews(newsModel: NewsModel): Boolean {

        val newsEntity = FavoriteNewsEntity(
            id = newsModel.hashCode(),
            title = newsModel.title,
            link = newsModel.link,
            creator = newsModel.creator,
            content = newsModel.content,
            pubDate = newsModel.pubDate,
            imageURL = newsModel.imageURL,
        )

        return favoriteNewsStorage.addNews(newsEntity)
    }

    override fun getFavoriteNews(): Flow<List<NewsModel>> {
        val result = favoriteNewsStorage.getNews()

        return result.map {
            val list = mutableListOf<NewsModel>()
            if (it.isNotEmpty()) {
                it.forEach {item ->  list.add(item.toFavoriteNewsModel())}
            }
            list
        }
    }

    override suspend fun deleteFavoriteNews(key: NewsModel): Boolean {
        return favoriteNewsStorage.deleteNews(key.hashCode())
    }
}