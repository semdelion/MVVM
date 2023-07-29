package com.semdelion.presentaion.core.sideeffects.navigator.plugin

import android.content.Context
import com.semdelion.presentaion.core.sideeffects.SideEffectMediator
import com.semdelion.presentaion.core.sideeffects.SideEffectPlugin
import com.semdelion.presentaion.core.sideeffects.navigator.Navigator

class NavigatorPlugin(
    private val navigator: Navigator,
) : SideEffectPlugin<Navigator, Navigator> {

    override val mediatorClass: Class<Navigator>
        get() = Navigator::class.java

    override fun createMediator(applicationContext: Context): SideEffectMediator<Navigator> {
        return NavigatorSideEffectMediator()
    }

    override fun createImplementation(mediator: Navigator): Navigator {
        return navigator
    }

}