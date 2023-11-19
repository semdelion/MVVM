package com.semdelion.data.apis.news

import com.semdelion.domain.repositories.news.models.NewsModel
import com.squareup.moshi.Json

data class NewsDataModel(
    val title: String,
    val link: String,
    val keywords: List<String>?,
    val creator: List<String>?,

    @Json(name="video_url")
    val videoURL: String? = null,

    val description: String? = null,
    val content: String? = null,
    val pubDate: String,

    @Json(name="image_url")
    val imageURL: String? = null,

    @Json(name="source_id")
    val sourceID: String,

    val category: List<String>,
    val country: List<String>,
    val language: String
)

fun NewsDataModel.toNewsModel() : NewsModel {
    return NewsModel(
        title = this.title,
        link = this.link,
        creator = this.creator ?: listOf(),
        videoURL = this.videoURL ?: "",
        description = this.description ?: "",
        content = this.content ?: "",
        pubDate = this.pubDate,
        imageURL = this.imageURL ?: "",
    )
}