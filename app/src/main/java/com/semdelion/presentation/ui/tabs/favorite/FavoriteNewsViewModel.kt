package com.semdelion.presentation.ui.tabs.favorite

import androidx.lifecycle.MutableLiveData
import com.semdelion.domain.repositories.news.models.NewsModel
import com.semdelion.domain.usecases.news.GetFavoriteNewsUseCase
import com.semdelion.presentation.core.sideeffects.navigator.utils.NavCommandDirections
import com.semdelion.presentation.core.sideeffects.navigator.Navigator
import com.semdelion.presentation.core.utils.toLiveData
import com.semdelion.presentation.core.viewmodels.BaseListViewModel
import com.semdelion.presentation.core.viewmodels.ListViewState
import com.semdelion.presentation.ui.tabs.news.navigation.NewsNavigationArg
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emitAll

class FavoriteNewsViewModel(
    private val getFavoriteNewsUseCase: GetFavoriteNewsUseCase,
    private val navigationService: Navigator,
) : BaseListViewModel() {
    private val _items = MutableStateFlow<MutableList<NewsModel>>(mutableListOf())
    val items = _items.asStateFlow()

    init {
        loadFavoriteNews()
    }

    fun onItemClick(news: NewsModel) {
        val navArg = NewsNavigationArg(
            title = news.title,
            link = news.link,
            creator = news.creator,
            content = news.content,
            pubDate = news.pubDate,
            imageURL = news.imageURL
        )
        navigationService.launch(
            NavCommandDirections(
            FavoriteNewsFragmentDirections.actionFavoriteNewsFragmentToFavoriteNewsDetailsFragment(
                navArg
            ))
        )
    }

    private fun loadFavoriteNews() {
        viewModelScope.launch {
            _viewState.emit(ListViewState.Loading)
            getFavoriteNewsUseCase.getFavoriteNews().collectLatest {
                try {
                    _items.emit(it.toMutableList())
                    _viewState.emit(ListViewState.Success)
                } catch (ex: Exception) {
                    _viewState.emit(ListViewState.Error(ex))
                }
            }

        }
    }
}