package com.semdelion.presentaion.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.semdelion.domain.models.Message
import com.semdelion.domain.repositories.IMessageRepository
import com.semdelion.presentaion.core.sideeffects.navigator.Navigator
import com.semdelion.presentaion.core.sideeffects.toasts.Toasts
import com.semdelion.presentaion.core.viewmodels.BaseViewModel
import com.semdelion.presentaion.views.SecondFragment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SecondViewModel(
    screen: SecondFragment.Screen,
    private val navigationService: Navigator,
    private val toasts: Toasts,
    private val messageRepository: IMessageRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _messageLive = MutableStateFlow(screen.message)
    val messageLive: StateFlow<String> = _messageLive

    val resultLive = MutableLiveData("")

    fun onBack() {
        saveMessage()
    }

    private fun saveMessage() {
        if (resultLive.value == null) {
            navigationService.goBack(resultLive.value)
        } else {
            viewModelScope.launch {
                messageRepository.saveMessage(Message(resultLive.value ?: "")).also { result ->
                    if (result)
                        navigationService.goBack(resultLive.value)
                    else
                        toasts.toast("Oops something wrong!")
                }
            }
        }
    }
}
