package com.semdelion.presentaion.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.semdelion.domain.core.tasks.dispatchers.Dispatcher
import com.semdelion.domain.models.ErrorResult
import com.semdelion.domain.models.Message
import com.semdelion.domain.models.SuccessResult
import com.semdelion.domain.repositories.IMessageRepository
import com.semdelion.presentaion.core.sideeffects.navigator.Navigator
import com.semdelion.presentaion.core.sideeffects.toasts.Toasts
import com.semdelion.presentaion.core.viewmodels.BaseViewModel
import com.semdelion.presentaion.views.SecondFragment

class SecondViewModel(
    screen: SecondFragment.Screen,
    private val navigationService: Navigator,
    private val toasts: Toasts,
    private val messageRepository: IMessageRepository,
    savedStateHandle: SavedStateHandle,
    dispatcher: Dispatcher
) : BaseViewModel(dispatcher) {

    private val _messageLive = MutableLiveData<String>(screen.message)
    val messageLive: LiveData<String> = _messageLive

    val resultLive = MutableLiveData<String>("")

    fun onBack() {
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
                            toasts.toast("Oops something wrong!")
                    }
                    is ErrorResult -> toasts.toast(result.exception.message ?: "Error")
                }
            }
        }
    }
}