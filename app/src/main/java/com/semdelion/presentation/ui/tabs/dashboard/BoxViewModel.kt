package com.semdelion.presentation.ui.tabs.dashboard

import android.Manifest
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.findNavController
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
import kotlinx.coroutines.launch
import kotlin.random.Random

class BoxViewModel(
    private val navigationService: Navigator,
) : BaseViewModel() {

    fun goBack() = navigationService.goBack()

    fun goSecretBox() = navigationService.launch(
        NavCommandDirections(BoxFragmentDirections.actionBoxFragmentToSecretFragment()))

    fun goBackWithRandom() {
        navigationService.goBack(Random.nextInt(100))
    }
}