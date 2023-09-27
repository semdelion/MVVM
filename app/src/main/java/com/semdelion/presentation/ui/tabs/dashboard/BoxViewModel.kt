package com.semdelion.presentation.ui.tabs.dashboard

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.findNavController
import com.semdelion.presentation.core.sideeffects.navigator.Navigator
import com.semdelion.presentation.core.sideeffects.toasts.Toasts
import com.semdelion.presentation.core.viewmodels.BaseViewModel
import kotlin.random.Random

class BoxViewModel(
    private val navigationService: Navigator,
    private val toasts: Toasts,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    fun goBack() = navigationService.goBack()

    fun goSecretBox() = navigationService.launch(BoxFragmentDirections.actionBoxFragmentToSecretFragment())

    fun goBackWithRandom() {
        navigationService.goBack(Random.nextInt(100))
    }
}