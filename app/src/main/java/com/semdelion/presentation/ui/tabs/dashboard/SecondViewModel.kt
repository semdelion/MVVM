package com.semdelion.presentation.ui.tabs.dashboard

import androidx.lifecycle.SavedStateHandle
import com.semdelion.domain.repositories.message.models.Message
import com.semdelion.domain.repositories.message.IMessageRepository
import com.semdelion.presentation.core.sideeffects.navigator.Navigator
import com.semdelion.presentation.core.sideeffects.toasts.Toasts
import com.semdelion.presentation.core.viewmodels.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SecondViewModel(
    private val navigationService: Navigator,
    private val toasts: Toasts,
    private val messageRepository: IMessageRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    val messageFlow: StateFlow<String> = savedStateHandle.getStateFlow("message", "")

    val resultFlow = MutableStateFlow("")

    fun onBack() {
        saveMessage()
    }

    private fun saveMessage() {
        viewModelScope.launch {
            messageRepository.saveMessage(Message(resultFlow.value)).also { result ->
                if (result)
                    navigationService.goBack(resultFlow.value)
                else
                    toasts.toast("Oops something wrong!")
            }
        }
    }
}
