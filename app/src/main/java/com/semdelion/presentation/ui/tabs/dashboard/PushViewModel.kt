package com.semdelion.presentation.ui.tabs.dashboard

import androidx.lifecycle.SavedStateHandle
import com.semdelion.domain.repositories.notification.INotificationRepository
import com.semdelion.domain.repositories.notification.models.NotificationData
import com.semdelion.domain.repositories.notification.models.NotificationMessage
import com.semdelion.domain.repositories.notification.models.PushNotification
import com.semdelion.presentation.core.sideeffects.navigator.Navigator
import com.semdelion.presentation.core.sideeffects.toasts.Toasts
import com.semdelion.presentation.core.viewmodels.BaseViewModel
import com.semdelion.presentation.core.viewmodels.ListViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

private const val TOPIC = "/topics/semdelionTopics"

class PushViewModel(
    private val navigation: Navigator,
    private val toasts: Toasts,
    private val notificationRepository: INotificationRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    val titleFlow = MutableStateFlow("")
    val contextFlow = MutableStateFlow("")
    val tokenFlow = MutableStateFlow("")

    fun sendPush() {
        if (titleFlow.value.isEmpty() || contextFlow.value.isEmpty()) {
            toasts.toast("title and context can't be empty")
            return
        }
        val notificationData = NotificationData(titleFlow.value, contextFlow.value)
         viewModelScope.launch {
             try {
                 notificationRepository.sendNotification(
                     PushNotification(
                         NotificationMessage(tokenFlow.value.ifEmpty { "/topics/semdelionTopics" },
                             notificationData
                         )
                     )
                 )
             } catch (ex: Exception) {
                 toasts.toast(ex.message ?: "")
             }
         }
    }
}