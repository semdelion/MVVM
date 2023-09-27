package com.semdelion.presentation.ui.base

sealed class ListViewState {
    object Loading : ListViewState()
    object Success : ListViewState()
    data class Error(val error: Exception) : ListViewState()
}
