package com.semdelion.data.repositories

import android.os.Build
import androidx.annotation.RequiresExtension
import com.semdelion.data.core.services.BaseService
import com.semdelion.data.core.services.client.ApiClient
import com.semdelion.data.services.news.INewsServices
import com.semdelion.data.services.news.toNewsModel
import com.semdelion.domain.core.coroutines.IoDispatcher
import com.semdelion.domain.repositories.news.models.NewsModel
import com.semdelion.domain.repositories.news.models.NewsPageModel
import com.semdelion.domain.repositories.news.INewsRepository
import retrofit2.awaitResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    apiClient: ApiClient,
    private val dispatcher: IoDispatcher
) : BaseService(apiClient), INewsRepository {

    private val _newsService = apiClient.createService(INewsServices::class.java)

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getNews(page: String?): NewsPageModel = wrapRetrofitExceptions(dispatcher.value) {
        val response = _newsService.getNewsAsync(page).awaitResponse()

        val newsModel = response.body()?.results ?: mutableListOf()

        val newsModels: MutableList<NewsModel> = mutableListOf()
        newsModel.forEach { newsModels.add(it.toNewsModel()) }
        NewsPageModel(newsModels, response.body()?.nextPage)
    }
}