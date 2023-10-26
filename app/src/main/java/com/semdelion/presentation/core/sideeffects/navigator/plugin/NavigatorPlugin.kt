package com.semdelion.presentation.core.sideeffects.navigator.plugin

import android.content.Context
import com.semdelion.presentation.core.sideeffects.SideEffectMediator
import com.semdelion.presentation.core.sideeffects.SideEffectPlugin
import com.semdelion.presentation.core.sideeffects.dialogs.plugin.DialogsSideEffectMediator
import com.semdelion.presentation.core.sideeffects.navigator.Navigator

class NavigatorPlugin(
    private val navigator: Navigator,
) : SideEffectPlugin<Navigator, Navigator> {

    override val mediatorClass: Class<Navigator>
        get() = Navigator::class.java

    private var current: SideEffectMediator<Navigator>? = null

    override fun createMediator(applicationContext: Context): SideEffectMediator<Navigator> {
        if(current == null)
            current = NavigatorSideEffectMediator()
        return current!!
    }

    override fun createImplementation(mediator: Navigator): Navigator {
        return navigator
    }

}