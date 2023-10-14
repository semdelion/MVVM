package com.semdelion.presentation.core.sideeffects.navigator.plugin

import com.semdelion.presentation.core.sideeffects.SideEffectMediator
import com.semdelion.presentation.core.sideeffects.navigator.utils.INavCommand
import com.semdelion.presentation.core.sideeffects.navigator.Navigator

class NavigatorSideEffectMediator : SideEffectMediator<Navigator>(), Navigator {

    override fun launch(navCommand: INavCommand) = target {
        it.launch(navCommand)
    }

    override fun goBack(result: Any?) = target {
        it.goBack(result)
    }
}