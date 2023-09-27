package com.semdelion.presentation.ui.tabs.dashboard

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.findNavController
import com.semdelion.presentation.core.sideeffects.navigator.Navigator
import com.semdelion.presentation.core.sideeffects.toasts.Toasts
import com.semdelion.presentation.core.viewmodels.BaseViewModel

class RootViewModel(
    private val navigationService: Navigator,
    private val toasts: Toasts,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    fun openBox(color: Int, colorName: String) {
        val direction = RootFragmentDirections.actionRootFragmentToBoxFragment(color, colorName)
        navigationService.launch(direction)
    }

    override fun onResult(result: Any) {
        super.onResult(result)
        if(result is Int) {
            toasts.toast("Generate number: $result")
        }
    }
}