package com.semdelion.data.services.news

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface INewsServices {
    @GET("1/news")
    suspend fun getNews(@Query("page") nextPage: String?, @Query("language") language: String = "en"): NewsPageResult

    @GET("1/news")
    fun getNewsAsync(@Query("page") nextPage: String?, @Query("language") language: String = "en"): Call<NewsPageResult>
}