package com.semdelion.presentation.ui.tabs.dashboard

import com.semdelion.presentation.core.sideeffects.navigator.utils.NavCommandDirections
import com.semdelion.presentation.core.sideeffects.navigator.Navigator
import com.semdelion.presentation.core.sideeffects.toasts.Toasts
import com.semdelion.presentation.core.viewmodels.BaseViewModel

class RootViewModel(
    private val navigationService: Navigator,
    private val toasts: Toasts
) : BaseViewModel() {

    fun openBox(color: Int, colorName: String) {
        val navCommand = NavCommandDirections(RootFragmentDirections.actionRootFragmentToBoxFragment(color, colorName))
        navigationService.launch(navCommand)
    }

    fun toFirstFragment() {
        val navCommand = NavCommandDirections(RootFragmentDirections.actionRootFragmentToFirstFragment2())
        navigationService.launch(navCommand)
    }

    override fun onResult(result: Any) {
        super.onResult(result)
        if(result is Int) {
            toasts.toast("Generate number: $result")
        }
    }
}