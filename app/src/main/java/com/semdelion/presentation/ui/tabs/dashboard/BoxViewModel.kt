package com.semdelion.presentation.ui.tabs.dashboard

import com.semdelion.presentation.core.sideeffects.navigator.utils.NavCommandDirections
import com.semdelion.presentation.core.sideeffects.navigator.Navigator
import com.semdelion.presentation.core.viewmodels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class BoxViewModel @Inject constructor(
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