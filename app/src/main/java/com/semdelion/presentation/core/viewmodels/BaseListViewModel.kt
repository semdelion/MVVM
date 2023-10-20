package com.semdelion.presentation.core.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class ListViewState {
    object Loading : ListViewState()
    object Success : ListViewState()
    data class Error(val error: Exception) : ListViewState()
}

abstract class BaseListViewModel() : BaseViewModel() {
    protected val _viewState = MutableStateFlow<ListViewState>(ListViewState.Success)
    val viewState = _viewState.asStateFlow()
    var isLoading: Boolean = false

    protected suspend fun getItemsWithState(getItems: suspend () -> Unit) {
        try {
            isLoading = true
            _viewState.emit(ListViewState.Loading)
            getItems.invoke()
            _viewState.emit(ListViewState.Success)
        } catch (ex: Exception) {
            _viewState.emit(ListViewState.Error(ex))
        } finally {
            isLoading = false
        }
    }
}