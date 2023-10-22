package com.semdelion.presentation.ui.tabs.favorite

import androidx.lifecycle.SavedStateHandle
import com.semdelion.domain.usecases.news.DeleteNewsUseCase
import com.semdelion.presentation.core.sideeffects.navigator.Navigator
import com.semdelion.presentation.core.sideeffects.toasts.Toasts
import com.semdelion.presentation.core.viewmodels.BaseViewModel
import com.semdelion.presentation.ui.tabs.news.navigation.NewsNavigationArg
import com.semdelion.presentation.ui.tabs.news.navigation.toNewsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteNewsDetailsViewModel(
    private val deleteNewsUseCase: DeleteNewsUseCase,
    private val navigationService: Navigator,
    private val toasts: Toasts,
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

    private val _deleteNewsState = MutableSharedFlow<String>()
    val deleteNewsState = _deleteNewsState.asSharedFlow()

    fun deleteFavoriteNews() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = deleteNewsUseCase.delete(newsNavigationArg.toNewsModel())
            toasts.toast(if (result) "Successful delete!" else "Failure delete!")
            navigationService.goBack()
        }
    }
}