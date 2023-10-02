package com.semdelion.domain.repositories.news.models

data class NewsPageModel(
    val News: List<NewsModel>,
    val nextPage: String?)