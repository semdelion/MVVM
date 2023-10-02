package com.semdelion.presentation.ui.tabs.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.semdelion.domain.repositories.news.models.NewsModel
import com.semdelion.domain.usecases.news.GetFavoriteNewsUseCase
import com.semdelion.presentation.core.sideeffects.navigator.Navigator
import com.semdelion.presentation.core.sideeffects.toasts.Toasts
import com.semdelion.presentation.core.viewmodels.BaseListViewModel
import com.semdelion.presentation.core.viewmodels.ListViewState
import com.semdelion.presentation.ui.tabs.news.navigation.NewsNavigationArg

import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest

class FavoriteNewsViewModel(
    private val getFavoriteNewsUseCase: GetFavoriteNewsUseCase,
    private val navigationService: Navigator,
    private val toasts: Toasts,
    savedStateHandle: SavedStateHandle
) : BaseListViewModel() {
    private val _items = MutableLiveData<MutableList<NewsModel>>()
    val items: LiveData<MutableList<NewsModel>> = _items

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
            FavoriteNewsFragmentDirections.actionFavoriteNewsFragmentToFavoriteNewsDetailsFragment(
                navArg
            )
        )
    }

    private fun loadFavoriteNews() {
        viewModelScope.launch {
            _viewState.emit(ListViewState.Loading)
            getFavoriteNewsUseCase.getFavoriteNews().collectLatest {
                try {
                    _items.postValue(it.toMutableList())
                    _viewState.emit(ListViewState.Success)
                } catch (ex: Exception) {
                    _viewState.emit(ListViewState.Error(ex))
                }
            }

        }
    }
}