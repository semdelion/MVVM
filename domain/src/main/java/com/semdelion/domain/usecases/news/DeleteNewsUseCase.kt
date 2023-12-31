package com.semdelion.domain.usecases.news

import com.semdelion.domain.repositories.news.models.NewsModel
import com.semdelion.domain.repositories.news.IFavoriteNewsRepository

class DeleteNewsUseCase(private val favoriteNews: IFavoriteNewsRepository) {
    suspend fun delete(newsModel: NewsModel): Boolean {
        return favoriteNews.deleteFavoriteNews(newsModel)
    }
}