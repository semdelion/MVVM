package com.semdelion.data.apis.news

data class NewsPageResult(
    val status: String,
    val nextPage: String,
    val results: List<NewsDataModel>
)