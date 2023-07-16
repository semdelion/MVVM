package com.semdelion.mvvm.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.semdelion.mvvm.presentation.navigations.Navigator
import com.semdelion.mvvm.presentation.navigations.UiActions
import com.semdelion.mvvm.presentation.viewmodels.base.BaseViewModel
import com.semdelion.mvvm.presentation.views.SecondFragment

class SecondViewModel(
    screen: SecondFragment.Screen,
    private val navigationService: Navigator,
    private val uiActions: UiActions
) : BaseViewModel() {

    private val _messageLive = MutableLiveData<String>(screen.message)
    val messageLive: LiveData<String> = _messageLive

    val resultLive = MutableLiveData<String>("")

    fun onBackPressed() {
        navigationService.goBack(resultLive.value)
    }
}