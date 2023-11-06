package com.semdelion.presentation.ui.tabs.dashboard.services

import com.semdelion.presentation.core.sideeffects.navigator.Navigator
import com.semdelion.presentation.core.viewmodels.BaseViewModel

class ServicesViewModel (
    private val navigationService: Navigator
) : BaseViewModel() {

    fun goBack() {
        navigationService.goBack()
    }
}