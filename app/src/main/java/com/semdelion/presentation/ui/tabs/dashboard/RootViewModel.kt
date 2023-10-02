package com.semdelion.presentation.ui.tabs.dashboard

import android.Manifest
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.findNavController
import com.semdelion.presentation.R
import com.semdelion.presentation.core.sideeffects.dialogs.Dialogs
import com.semdelion.presentation.core.sideeffects.dialogs.plugin.DialogConfig
import com.semdelion.presentation.core.sideeffects.intents.Intents
import com.semdelion.presentation.core.sideeffects.navigator.Navigator
import com.semdelion.presentation.core.sideeffects.permissions.Permissions
import com.semdelion.presentation.core.sideeffects.permissions.plugin.PermissionStatus
import com.semdelion.presentation.core.sideeffects.resources.Resources
import com.semdelion.presentation.core.sideeffects.toasts.Toasts
import com.semdelion.presentation.core.viewmodels.BaseViewModel
import kotlinx.coroutines.launch

class RootViewModel(
    private val navigationService: Navigator,
    private val toasts: Toasts,
    private val resources: Resources,
    private val permissions: Permissions,
    private val intents: Intents,
    private val dialogs: Dialogs,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    fun openBox(color: Int, colorName: String) {
        val direction = RootFragmentDirections.actionRootFragmentToBoxFragment(color, colorName)
        navigationService.launch(direction)
    }

    fun toFirstFragment() {
        val direction = RootFragmentDirections.actionRootFragmentToFirstFragment2()
        navigationService.launch(direction)
    }

    override fun onResult(result: Any) {
        super.onResult(result)
        if(result is Int) {
            toasts.toast("Generate number: $result")
        }
    }
}