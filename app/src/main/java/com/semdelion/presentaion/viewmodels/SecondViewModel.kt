package com.semdelion.presentaion.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.semdelion.presentaion.core.navigator.Navigator
import com.semdelion.presentaion.core.uiactions.UiActions
import com.semdelion.presentaion.core.viewmodels.BaseViewModel
import com.semdelion.presentaion.views.SecondFragment

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