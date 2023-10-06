package com.semdelion.presentation.ui.tabs.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.semdelion.domain.usecases.news.SaveNewsUseCase
import com.semdelion.presentation.core.sideeffects.toasts.Toasts
import com.semdelion.presentation.core.utils.toLiveData
import com.semdelion.presentation.core.viewmodels.BaseViewModel
import com.semdelion.presentation.ui.tabs.news.navigation.NewsNavigationArg
import com.semdelion.presentation.ui.tabs.news.navigation.toNewsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class NewsDetailsViewModel(
    private val saveNewsUseCase: SaveNewsUseCase,
    val toasts: Toasts,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val newsNavigationArg: NewsNavigationArg

    init {
        newsNavigationArg = savedStateHandle.get<NewsNavigationArg>("newsItem")
            ?: throw IllegalArgumentException("NewsDetailsViewModel args is null by key \"newsItem\"")
    }

    val imageUrl: String = newsNavigationArg.imageURL

    private val _newsLive = MutableLiveData(newsNavigationArg)
    val newsLive = _newsLive.toLiveData()

    private val _titleLive = MutableLiveData(newsNavigationArg.title)
    val titleLive = _titleLive.toLiveData()

    private val _contentLive = MutableLiveData(newsNavigationArg.content)
    val contentLive = _contentLive.toLiveData()

    private val _dateLive = MutableLiveData(newsNavigationArg.pubDate)
    val dateLive = _dateLive.toLiveData()

    val creators: List<String> = newsNavigationArg.creator

    val link: String = newsNavigationArg.link

    fun addToFavoriteNews() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = saveNewsUseCase.save(newsNavigationArg.toNewsModel())
            toasts.toast(if (result) "Successful save!" else "Failure save!")
        }
    }
}