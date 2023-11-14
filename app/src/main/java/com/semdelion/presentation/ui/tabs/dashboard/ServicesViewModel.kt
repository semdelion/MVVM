package com.semdelion.presentation.ui.tabs.dashboard

import android.Manifest
import android.os.Build
import androidx.core.app.ActivityCompat
import com.semdelion.presentation.core.sideeffects.dialogs.Dialogs
import com.semdelion.presentation.core.sideeffects.navigator.Navigator
import com.semdelion.presentation.core.sideeffects.permissions.Permissions
import com.semdelion.presentation.core.sideeffects.permissions.plugin.PermissionStatus
import com.semdelion.presentation.core.sideeffects.toasts.Toasts
import com.semdelion.presentation.core.viewmodels.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ServicesViewModel (
    private val navigationService: Navigator,
    private val toasts: Toasts,
    private val permissions: Permissions,
    private val dialogs: Dialogs
) : BaseViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val permission = Manifest.permission.POST_NOTIFICATIONS
                val hasPermission = permissions.hasPermissions(permission)
                if (!hasPermission) {
                    permissions.requestPermission(permission)
                }
            }
        }
    }

    fun goBack() {
        navigationService.goBack()
    }
}