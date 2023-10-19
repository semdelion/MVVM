package com.semdelion.presentation.ui.tabs.news

import androidx.lifecycle.SavedStateHandle
import com.semdelion.domain.usecases.news.SaveNewsUseCase
import com.semdelion.presentation.core.sideeffects.toasts.Toasts
import com.semdelion.presentation.core.viewmodels.BaseViewModel
import com.semdelion.presentation.ui.tabs.news.navigation.NewsNavigationArg
import com.semdelion.presentation.ui.tabs.news.navigation.toNewsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _titleFlow = MutableStateFlow(newsNavigationArg.title)
    val titleFlow = _titleFlow.asStateFlow()

    private val _contentFlow = MutableStateFlow(newsNavigationArg.content)
    val contentFlow = _contentFlow.asStateFlow()

    private val _dateFlow = MutableStateFlow(newsNavigationArg.pubDate)
    val dateFlow = _dateFlow.asStateFlow()

    val creators: List<String> = newsNavigationArg.creator

    val link: String = newsNavigationArg.link

    fun addToFavoriteNews() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = saveNewsUseCase.save(newsNavigationArg.toNewsModel())
            toasts.toast(if (result) "Successful save!" else "Failure save!")
        }
    }
}