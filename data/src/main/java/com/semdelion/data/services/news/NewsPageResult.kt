package com.semdelion.data.services.news

data class NewsPageResult(
    val status: String,
    val nextPage: String,
    val results: List<NewsDataModel>
)