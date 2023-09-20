package com.semdelion.presentaion.viewmodels

import android.Manifest
import androidx.lifecycle.SavedStateHandle
import com.semdelion.domain.repositories.IMessageRepository
import com.semdelion.presentaion.core.sideeffects.dialogs.Dialogs
import com.semdelion.presentaion.core.sideeffects.dialogs.plugin.DialogConfig
import com.semdelion.presentaion.core.sideeffects.intents.Intents
import com.semdelion.presentaion.core.sideeffects.navigator.Navigator
import com.semdelion.presentaion.core.sideeffects.permissions.Permissions
import com.semdelion.presentaion.core.sideeffects.permissions.plugin.PermissionStatus
import com.semdelion.presentaion.core.sideeffects.resources.Resources
import com.semdelion.presentaion.core.sideeffects.toasts.Toasts
import com.semdelion.presentaion.core.viewmodels.BaseViewModel
import com.semdelion.presentaion.views.FirstFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FirstViewModel(
    private val navigationService: Navigator,
    private val toasts: Toasts,
    private val resources: Resources,
    private val permissions: Permissions,
    private val intents: Intents,
    private val dialogs: Dialogs,
    private val messageRepository: IMessageRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _resultLive = MutableStateFlow("...")
    val resultLive: StateFlow<String> = _resultLive

    val messageLive = MutableStateFlow("")

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = messageRepository.getMessage()
            _resultLive.value = result.text
        }
    }

    fun sendText() {
        navigationService.launch(FirstFragmentDirections.actionFirstFragmentToSecondFragment(messageLive.value ?: ""))
    }

    fun requestPermission() = viewModelScope.launch {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        val hasPermission = permissions.hasPermissions(permission)
        if (hasPermission) {
            dialogs.show(createPermissionAlreadyGrantedDialog())
        } else {
            when (permissions.requestPermission(permission)) {
                PermissionStatus.GRANTED -> {
                    toasts.toast("GRANTED")
                }
                PermissionStatus.DENIED -> {
                    toasts.toast("DENIED")
                }
                PermissionStatus.DENIED_FOREVER -> {
                    if (dialogs.show(createAskForLaunchingAppSettingsDialog())) {
                        intents.openAppSettings()
                    }
                }
            }
        }
    }

    private fun createPermissionAlreadyGrantedDialog() = DialogConfig(
        title = "title",
        message ="already_granted",
        positiveButton = "ok"
    )

    private fun createAskForLaunchingAppSettingsDialog() = DialogConfig(
        title = "title",
        message = "message",
        positiveButton = "action_open",
        negativeButton = "cancel"
    )

    override fun onResult(result: Any) {
        super.onResult(result)
        _resultLive.value = (result as String)
        toasts.toast("Result from Second ${(result)}")
    }
}