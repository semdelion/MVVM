package com.semdelion.presentaion.core.utils

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

typealias viewModelCreator = () -> ViewModel

class ViewModelFactory(private val creator: viewModelCreator) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return creator() as T
    }
}

inline fun <reified VM : ViewModel> ComponentActivity.viewModelCreator(noinline creator: viewModelCreator): Lazy<VM> {
    return viewModels { ViewModelFactory(creator) }
}