package com.semdelion.domain.usecases.news

import com.semdelion.domain.repositories.news.models.NewsPageModel
import com.semdelion.domain.repositories.news.INewsRepository

class GetNewsUseCase(private val newsRepository: INewsRepository) {
    fun get(page:String? = null): NewsPageModel {
        return newsRepository.getNews(page)
    }
}
