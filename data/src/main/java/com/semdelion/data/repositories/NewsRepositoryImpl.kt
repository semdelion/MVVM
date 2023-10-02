package com.semdelion.data.repositories

import com.semdelion.data.core.BaseService
import com.semdelion.data.services.news.INewsServices
import com.semdelion.data.services.news.toNewsModel
import com.semdelion.domain.repositories.news.models.NewsModel
import com.semdelion.domain.repositories.news.models.NewsPageModel
import com.semdelion.domain.repositories.news.INewsRepository

class NewsRepositoryImpl : BaseService(), INewsRepository {

    private val _newsService = retrofit.create(INewsServices::class.java)

    override fun getNews(page: String?): NewsPageModel {

        val response = _newsService.getNews(page).execute()

        val newsModel = response.body()?.results ?: mutableListOf()

        val newsModels: MutableList<NewsModel> = mutableListOf()
        newsModel.forEach { newsModels.add(it.toNewsModel()) }
        return NewsPageModel(newsModels, response.body()?.nextPage)
    }
}