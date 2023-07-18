package com.semdelion.presentaion.core.navigator

import com.semdelion.presentaion.core.utils.ResourceActions
import com.semdelion.presentaion.core.views.utils.BaseScreen

class IntermediateNavigator: Navigator {

    private val targetNavigator = ResourceActions<Navigator>()

    override fun launch(screen: BaseScreen) = targetNavigator {
        it.launch(screen)
    }

    override fun goBack(result: Any?) = targetNavigator {
        it.goBack(result)
    }

    fun setTarget(navigator: Navigator?) {
        targetNavigator.resource = navigator
    }

    fun clear() {
        targetNavigator.clear()
    }
}