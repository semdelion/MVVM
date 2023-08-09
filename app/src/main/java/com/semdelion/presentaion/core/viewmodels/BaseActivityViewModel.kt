package com.semdelion.presentaion.core.viewmodels

import androidx.lifecycle.ViewModel
import com.semdelion.presentaion.core.sideeffects.SideEffectMediator
import com.semdelion.presentaion.core.sideeffects.SideEffectMediatorsHolder

class BaseActivityViewModel  : ViewModel() {

    internal val sideEffectMediatorsHolder = SideEffectMediatorsHolder()

    // contains the list of side-effect mediators that can be
    // passed to view-model constructors
    val sideEffectMediators: List<SideEffectMediator<*>>
        get() = sideEffectMediatorsHolder.mediators

    override fun onCleared() {
        super.onCleared()
        sideEffectMediatorsHolder.clear()
    }

}