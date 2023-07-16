package com.semdelion.mvvm.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.semdelion.mvvm.presentation.navigations.Navigator
import com.semdelion.mvvm.presentation.navigations.UiActions
import com.semdelion.mvvm.presentation.viewmodels.base.BaseViewModel
import com.semdelion.mvvm.presentation.views.FirstFragment
import com.semdelion.mvvm.presentation.views.SecondFragment

class FirstViewModel(
    screen: FirstFragment.Screen,
    private val navigationService: Navigator,
    private val uiActions: UiActions,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    fun sendText() {
        val screen = SecondFragment.Screen("")
        navigationService.launch(screen)
    }
}