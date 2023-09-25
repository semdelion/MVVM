package com.semdelion.presentation.core.sideeffects.navigator.plugin

import androidx.navigation.NavDirections
import com.semdelion.presentation.core.sideeffects.SideEffectMediator
import com.semdelion.presentation.core.sideeffects.navigator.Navigator

class NavigatorSideEffectMediator : SideEffectMediator<Navigator>(), Navigator {

    override fun launch(direction: NavDirections) = target {
        it.launch(direction)
    }

    override fun goBack(result: Any?) = target {
        it.goBack(result)
    }
}