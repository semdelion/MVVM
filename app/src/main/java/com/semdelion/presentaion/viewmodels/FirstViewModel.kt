package com.semdelion.presentaion.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.semdelion.presentaion.navigation.Navigator
import com.semdelion.presentaion.navigation.UiActions
import com.semdelion.presentaion.viewmodels.base.BaseViewModel
import com.semdelion.presentaion.views.FirstFragment
import com.semdelion.presentaion.views.SecondFragment

class FirstViewModel(
    screen: FirstFragment.Screen,
    private val navigationService: Navigator,
    private val uiActions: UiActions,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _resultLive = MutableLiveData("...")
    val resultLive: LiveData<String> = _resultLive

    val messageLive = MutableLiveData<String>("")

    fun sendText() {
        val screen = SecondFragment.Screen(messageLive.value?:"")
        navigationService.launch(screen)
    }

    override fun onResult(result: Any) {
        super.onResult(result)
        _resultLive.value = (result as String)
        uiActions.toast("Result from Second ${(result)}")
    }
}