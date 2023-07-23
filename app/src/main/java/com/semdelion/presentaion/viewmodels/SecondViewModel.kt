package com.semdelion.presentaion.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.semdelion.domain.core.TasksFactory
import com.semdelion.domain.models.ErrorResult
import com.semdelion.domain.models.Message
import com.semdelion.domain.models.SuccessResult
import com.semdelion.domain.repositories.IMessageRepository
import com.semdelion.presentaion.core.navigator.Navigator
import com.semdelion.presentaion.core.uiactions.UiActions
import com.semdelion.presentaion.core.viewmodels.BaseViewModel
import com.semdelion.presentaion.views.SecondFragment

class SecondViewModel(
    screen: SecondFragment.Screen,
    private val navigationService: Navigator,
    private val uiActions: UiActions,
    private val messageRepository: IMessageRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _messageLive = MutableLiveData<String>(screen.message)
    val messageLive: LiveData<String> = _messageLive

    val resultLive = MutableLiveData<String>("")

    fun onBackPressed() {
        saveMessage()
    }

    private fun saveMessage() {
        if(resultLive.value == null) {
            navigationService.goBack(resultLive.value)
        }
        else {
            messageRepository.saveMessage(Message(resultLive.value ?: "")).safeEnqueue {result ->
                when(result) {
                    is SuccessResult -> {
                        if(result.data)
                            navigationService.goBack(resultLive.value)
                        else
                            uiActions.toast("Oops something wrong!")
                    }
                    is ErrorResult -> uiActions.toast(result.exception.message ?: "Error")
                }
            }
        }
    }
}