package com.semdelion.domain.usecases.news

import com.semdelion.domain.repositories.news.models.NewsModel
import com.semdelion.domain.repositories.news.IFavoriteNewsRepository

class SaveNewsUseCase(private val favoriteNews: IFavoriteNewsRepository) {
    suspend fun save(newsModel: NewsModel): Boolean {
        return favoriteNews.addFavoriteNews(newsModel)
    }
}