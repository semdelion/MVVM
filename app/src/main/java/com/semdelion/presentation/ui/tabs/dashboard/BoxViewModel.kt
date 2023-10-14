package com.semdelion.presentation.ui.tabs.dashboard

import com.semdelion.presentation.core.sideeffects.navigator.utils.NavCommandDirections
import com.semdelion.presentation.core.sideeffects.navigator.Navigator
import com.semdelion.presentation.core.viewmodels.BaseViewModel
import kotlin.random.Random

class BoxViewModel(
    private val navigationService: Navigator,
) : BaseViewModel() {

    fun goBack() = navigationService.goBack()

    fun goSecretBox() = navigationService.launch(
        NavCommandDirections(BoxFragmentDirections.actionBoxFragmentToSecretFragment())
    )

    fun goBackWithRandom() {
        navigationService.goBack(Random.nextInt(100))
    }
}