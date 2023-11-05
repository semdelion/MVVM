package com.semdelion.presentation.core.sideeffects.navigator.plugin

import android.content.Context
import com.semdelion.presentation.core.sideeffects.SideEffectMediator
import com.semdelion.presentation.core.sideeffects.SideEffectPlugin
import com.semdelion.presentation.core.sideeffects.SideEffectProvider
import com.semdelion.presentation.core.sideeffects.navigator.Navigator
import dagger.hilt.android.EntryPointAccessors

class NavigatorPlugin(
    private val navigator: Navigator,
) : SideEffectPlugin<Navigator, Navigator> {

    override val mediatorClass: Class<Navigator>
        get() = Navigator::class.java


    override fun createMediator(applicationContext: Context): SideEffectMediator<Navigator> {
        val sideEffectProvider = EntryPointAccessors.fromApplication(applicationContext, SideEffectProvider::class.java)
        return sideEffectProvider.getNavigator() as SideEffectMediator<Navigator>
    }

    override fun createImplementation(mediator: Navigator): Navigator {
        return navigator
    }

}