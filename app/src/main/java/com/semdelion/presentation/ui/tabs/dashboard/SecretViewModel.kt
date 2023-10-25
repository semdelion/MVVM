package com.semdelion.presentation.ui.tabs.dashboard

import com.semdelion.presentation.core.sideeffects.navigator.Navigator
import com.semdelion.presentation.core.viewmodels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SecretViewModel @Inject constructor(
    private val navigationService: Navigator
) : BaseViewModel() {

    fun goBack() {
        navigationService.goBack()
    }
}