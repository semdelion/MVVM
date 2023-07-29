package com.semdelion.presentaion.viewmodels

import android.Manifest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.semdelion.domain.core.tasks.TasksFactory
import com.semdelion.domain.core.tasks.dispatchers.Dispatcher
import com.semdelion.domain.models.takeSuccess
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
import com.semdelion.presentaion.views.FirstFragment
import com.semdelion.presentaion.views.SecondFragment

class FirstViewModel(
    screen: FirstFragment.Screen,
    private val navigationService: Navigator,
    private val toasts: Toasts,
    private val resources: Resources,
    private val permissions: Permissions,
    private val intents: Intents,
    private val dialogs: Dialogs,
    private val tasksFactory: TasksFactory,
    private val messageRepository: IMessageRepository,
    savedStateHandle: SavedStateHandle,
    dispatcher: Dispatcher
) : BaseViewModel(dispatcher) {

    private val _resultLive = MutableLiveData("...")
    val resultLive: LiveData<String> = _resultLive

    val messageLive = MutableLiveData<String>("")

    init {
        messageRepository.getMessage().safeEnqueue {
            _resultLive.postValue(it.takeSuccess()?.text)
        }
    }

    fun sendText() {
        val screen = SecondFragment.Screen(messageLive.value ?: "")
        navigationService.launch(screen)
    }

    fun requestPermission() = tasksFactory.async<Unit> {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        val hasPermission = permissions.hasPermissions(permission)
        if (hasPermission) {
            dialogs.show(createPermissionAlreadyGrantedDialog()).await()
        } else {
            when (permissions.requestPermission(permission).await()) {
                PermissionStatus.GRANTED -> {
                    toasts.toast("GRANTED")
                }
                PermissionStatus.DENIED -> {
                    toasts.toast("DENIED")
                }
                PermissionStatus.DENIED_FOREVER -> {
                    if (dialogs.show(createAskForLaunchingAppSettingsDialog()).await()) {
                        intents.openAppSettings()
                    }
                }
            }
        }
    }.safeEnqueue()

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