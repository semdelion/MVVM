package com.semdelion.presentation.ui.tabs.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.semdelion.domain.repositories.news.models.NewsModel
import com.semdelion.domain.usecases.news.GetNewsUseCase
import com.semdelion.presentation.core.sideeffects.navigator.NavCommandDirections
import com.semdelion.presentation.core.sideeffects.navigator.Navigator
import com.semdelion.presentation.core.sideeffects.toasts.Toasts
import com.semdelion.presentation.core.viewmodels.BaseListViewModel
import com.semdelion.presentation.ui.tabs.news.navigation.NewsNavigationArg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewsViewModel(
    private val getNewsUseCase: GetNewsUseCase,
    private val navigationService: Navigator
    ) : BaseListViewModel() {
    private val _items = MutableLiveData<MutableList<NewsModel>>()
    val items: LiveData<MutableList<NewsModel>> = _items

    private var _nextPageId: String? = null

    init {
        loadNews(_nextPageId)
    }

    fun loadNextPage() : Job {
        return loadNews(_nextPageId)
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
        navigationService.launch(NavCommandDirections(NewsFragmentDirections.actionNewsFragmentToNewsDetailsFragment(navArg)))
    }

    fun refreshNews() : Job {
        _nextPageId = null
        return viewModelScope.launch(Dispatchers.IO) {
            getItemsWithState{
                val newsPageModel = getNewsUseCase.get(null)
                _nextPageId = newsPageModel.nextPage
                _items.postValue(newsPageModel.News.toMutableList())
            }
        }
    }

    private fun loadNews(page:String?): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            getItemsWithState {
                val itemsData = _items.value ?: mutableListOf()
                val newsPageModel = getNewsUseCase.get(page)
                _nextPageId = newsPageModel.nextPage
                itemsData.addAll(newsPageModel.News)
                _items.postValue(itemsData)
            }
        }
    }
}