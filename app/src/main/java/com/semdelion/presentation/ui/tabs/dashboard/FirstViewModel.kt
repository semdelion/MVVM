package com.semdelion.presentation.ui.tabs.dashboard

import android.Manifest
import com.semdelion.domain.repositories.message.IMessageRepository
import com.semdelion.presentation.R
import com.semdelion.presentation.core.sideeffects.dialogs.Dialogs
import com.semdelion.presentation.core.sideeffects.dialogs.plugin.DialogConfig
import com.semdelion.presentation.core.sideeffects.intents.Intents
import com.semdelion.presentation.core.sideeffects.navigator.NavCommandDirections
import com.semdelion.presentation.core.sideeffects.navigator.Navigator
import com.semdelion.presentation.core.sideeffects.permissions.Permissions
import com.semdelion.presentation.core.sideeffects.permissions.plugin.PermissionStatus
import com.semdelion.presentation.core.sideeffects.resources.Resources
import com.semdelion.presentation.core.sideeffects.toasts.Toasts
import com.semdelion.presentation.core.viewmodels.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FirstViewModel(
    private val navigationService: Navigator,
    private val toasts: Toasts,
    private val resources: Resources,
    private val permissions: Permissions,
    private val intents: Intents,
    private val dialogs: Dialogs,
    private val messageRepository: IMessageRepository
) : BaseViewModel() {

    private val _resultFlow = MutableStateFlow("...")
    val resultFlow = _resultFlow.asStateFlow()

    val messageFlow = MutableStateFlow("")

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = messageRepository.getMessage()
            _resultFlow.value = result.text
        }
    }

    fun sendText() {
        navigationService.launch(
            NavCommandDirections(FirstFragmentDirections.actionFirstFragmentToSecondFragment(
                messageFlow.value
            ))
        )
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
        title = resources.getString(R.string.dialog_permission_title),
        message = resources.getString(R.string.dialog_permission_granted_message),
        positiveButton = resources.getString(R.string.dialog_permission_granted_button_ok)
    )

    private fun createAskForLaunchingAppSettingsDialog() = DialogConfig(
        title = resources.getString(R.string.dialog_permission_title),
        message = resources.getString(R.string.dialog_permission_denied_message),
        positiveButton = resources.getString(R.string.dialog_permission_denied_button_ok),
        negativeButton = resources.getString(R.string.dialog_permission_denied_button_cancel)
    )

    override fun onResult(result: Any) {
        super.onResult(result)
        _resultFlow.value = (result as String)
        toasts.toast("Result from Second ${(result)}")
    }
}