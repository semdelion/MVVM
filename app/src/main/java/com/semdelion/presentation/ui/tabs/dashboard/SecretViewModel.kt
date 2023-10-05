package com.semdelion.presentation.ui.tabs.dashboard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.semdelion.presentation.core.sideeffects.navigator.Navigator
import com.semdelion.presentation.core.sideeffects.toasts.Toasts
import com.semdelion.presentation.core.viewmodels.BaseViewModel

class SecretViewModel(
    private val navigationService: Navigator
) : BaseViewModel() {

    fun goBack() {
        navigationService.goBack()
    }
}