package com.semdelion.domain.repositories.news

import com.semdelion.domain.repositories.news.models.NewsPageModel

interface INewsRepository {
    fun getNews(page: String?): NewsPageModel
}